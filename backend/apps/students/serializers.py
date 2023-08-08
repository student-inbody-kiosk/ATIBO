import re
import pytz
from datetime import date, datetime, timedelta

from django.conf import settings
from django.db.utils import IntegrityError
from django.utils.translation import gettext_lazy as _

from rest_framework import status, serializers

from atibo.exceptions import DetailException
from atibo.regexes import STUDENT_PASSWORD_REGEX
from atibo.utils.datetime_converter import datetime_to_date_time
from .models import Student, Attendance, Inbody


class StudentListAuthSerializer(serializers.ListSerializer):
    # Mulltiple Create
    def create(self, validated_data):
        students = [Student(**item) for item in validated_data]

        # Handle DB integrity error
        try:
            instance = Student.objects.bulk_create(students)
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{message}'), f'db_{code}')
        
        return instance
    
    # Multiple Update
    def update(self, instance, validated_data):
        students =[]
        for item in validated_data:
            student = Student(**item)
            """
            Restore the (grade, room, number) constraint, which is deactivated in the serializer
            """
            student.is_constraint_activated = True
            students.append(student)

        # Handle DB integrity error
        try:
            # Bulk update
            Student.objects.bulk_update(students, ['name', 'grade', 'room', 'number', 'sex', 'password', 'birth_date', 'is_constraint_activated'])
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{message}'), f'db_{code}')
            
        return students


class StudentAuthSerializer(serializers.ModelSerializer):
    id = serializers.UUIDField(required=False)    # For multiple update

    class Meta:
        list_serializer_class = StudentListAuthSerializer
        model = Student
        exclude = ['is_authenticated', 'is_constraint_activated']

    # Since this is constraints, and the default validator isn't set.
    def validate_sex(self, value):
        if not value in (0, 1, 2, 9):
            raise serializers.ValidationError(_('Check the gender'), 'invalid_sex')
        return value
    
    def validate_birth_date(self, value):
        if value > date.today():
            raise serializers.ValidationError(_('생일은 오늘을 기준으로 이전 이어야 합니다'), 'invalid_birth_date')
        return value


class StudentCheckSerializer(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = ['name', 'grade', 'room', 'number']


class StudentDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = Student
        fields = ['id', 'name', 'grade', 'room', 'number', 'sex', 'birth_date']


class StudentPasswordChangeSerializer(serializers.Serializer):
    old_password = serializers.CharField(write_only=True, required=True)
    new_password = serializers.CharField(write_only=True, required=True)
    confirm_password  = serializers.CharField(write_only=True, required=True)

    def validate_old_password(self, value):
        user = self.context['request'].user
        if not user.password == value:
            raise serializers.ValidationError(_('기존 비밀번호가 일치하지 않습니다'), 'invalid_old_password')
        return value
    
    def validate_new_password(self, value):
        if not re.compile(STUDENT_PASSWORD_REGEX).match(value):
            raise serializers.ValidationError(_('비빌번호는 4자리 숫자로 입력해주세요'), 'invalid_old_password')
        return value
    
    def validate(self, data):
        new_password = data.get('new_password')
        confirm_password = data.get('confirm_password')

        if new_password != confirm_password:
            raise serializers.ValidationError({"confirm_password": _('새 비밀번호가 서로 일치하지 않습니다')}, 'invalid_student_confirm_password')

        return data
    
    def update(self, instance, validated_data):
        new_password = validated_data['new_password']
        instance.password = new_password
        instance.save()
        return instance


class AttendanceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Attendance
        fields = ['id', 'date_attended']
        read_only_fields = ['id', 'date_attended']

    def to_representation(self, instance):
        ret = super().to_representation(instance)

        # Change the datetime format    
        date_attended = ret.get('date_attended')
        ret['date_attended'] = datetime_to_date_time(date_attended)

        return ret
    
    def create(self, validated_data):
        student = validated_data['student']

        # Attendance Check can be repeated within 30min
        attendance =  Attendance.objects.filter(student=student).order_by('-date_attended')[:1]

        if attendance:
            attendance = attendance[0]
            tz = pytz.timezone(settings.TIME_ZONE)
            now = datetime.now(tz=tz)
            time_interval = now - attendance.date_attended
            if time_interval < timedelta(minutes=30):
                time_interval_min = int(time_interval.total_seconds() / 60)
                raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'이미 {time_interval_min} 분 전에 출석되었습니다. 출석은 최소 30분마다 가능합니다'), 'too_fast_attendance')
            validated_data['date_attended'] = now

        return super().create(validated_data)


class StudentAttendanceSerializer(serializers.ModelSerializer):
    attendance_set = AttendanceSerializer(many=True, read_only=True)

    class Meta:
        model = Student
        fields = ['name', 'grade', 'room', 'number', 'attendance_set']

    # Group the attendnace data by date
    def to_representation(self, instance):
        ret = super().to_representation(instance)

        attendance_set = {}
        for attendnace in ret['attendance_set']:
            id, date_attended = attendnace['id'], attendnace['date_attended']
            date, time = date_attended.split()
            date = int(date[-2:])

            if date not in attendance_set:
                attendance_set[date] = []

            attendance_set[date].append({
                'id': id,
                'time': time,
            })

        ret['attendance_set'] = attendance_set
        return ret

class InbodyListSerializer(serializers.ListSerializer):    
    # Multiple Create/Update
    def update(self, instance, validated_data):
        inbody_mapping = {inbody.id: inbody for inbody in instance}
        data_mapping = {item['id']: item for item in validated_data}

        ret = []
        # Handle DB integrity error
        try:
            for inbody_id, data in data_mapping.items():
                student = inbody_mapping.get(inbody_id, None)
                if student is None: # Create
                    ret.append(self.child.create(data))
                else:   # Update
                    """
                    Restore the (grade, room, number) constraint, which is deactivated in the serializer
                    """
                    data['is_constraint_activated'] = True
                    ret.append(self.child.update(student, data))
                    
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{message}'), f'db_{code}')
            
        return ret


class InbodySerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(required=False)    # For multiple update

    class Meta:
        list_serializer_class = InbodyListSerializer
        model = Inbody
        exclude = ['student', 'is_constraint_activated']

    def create(self, validated_data):
        try:
            return super().create(validated_data)
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('해당 날짜에 인바디 기록이 이미 존재합니다'), f'db_{code}')

    def validate_test_date(self, value):
        if value > date.today():
            raise serializers.ValidationError(_('테스트 날짜가 오늘 이후일 수 없습니다'), 'invalid_birth_date')
        return value
    
    # Round to second decimal place
    def to_representation(self, instance):
        ret = super().to_representation(instance)
        for key, value in  ret.items():
            if key not in  {'id', 'student', 'test_date', 'score'}:
                ret[key] = round(value,2)
        return ret



class StudentInbodySerializer(serializers.ModelSerializer):
    inbody_set = InbodySerializer(many=True, read_only=True)

    class Meta:
        model = Student
        fields = ['name', 'grade', 'room', 'number', 'sex', 'inbody_set']
