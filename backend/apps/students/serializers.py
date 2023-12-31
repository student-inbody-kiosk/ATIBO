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
    # Multiple Create
    def create(self, validated_data):
        """
        Handle DB integrity Error
        No `bulk_create()` for the detailed error message 
        """
        ret = []
        try:
            for student in validated_data:
                ret.append(self.child.create(student))
        except IntegrityError:
            grade = student.get('grade')
            room = student.get('room')
            number = student.get('number')
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{grade}학년 {room}반 {number}번에 해당하는 학생이 이미 존재합니다.\n 학년, 반, 번호 조합은 고유해야 합니다.'), 'db_integrity_error') 
        return ret
    
    # Multiple Update
    def update(self, instance, validated_data):
        student_mapping = {student.id: student for student in instance}
        data_mapping = {item['id']: item for item in validated_data}
    
        """
        Handle DB integrity Error
        No `bulk_update()` for the detailed error message 
        """
        ret =[]
        try:
            for student_id, data in data_mapping.items():
                student = student_mapping.get(student_id)
                data['is_constraint_activated'] = True  # which was set as False in the views.py
                ret.append(self.child.update(student, data))
        except IntegrityError:
            grade = data.get('grade')
            room = data.get('room')
            number = data.get('number')
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{grade}학년 {room}반 {number}번에 해당하는 학생이 이미 존재합니다.\n 학년, 반, 번호 조합은 고유해야 합니다.'), 'db_integrity_error')
        return ret


class StudentAuthSerializer(serializers.ModelSerializer):
    id = serializers.UUIDField(required=False)    # For multiple update

    class Meta:
        list_serializer_class = StudentListAuthSerializer
        model = Student
        exclude = ['is_authenticated', 'is_constraint_activated']

    # Note: The constraints in Model field don't force validation check.
    def validate_sex(self, value):
        if value not in (0, 1, 2, 9):
            raise serializers.ValidationError(_('성별을 확인해주세요'), 'invalid_sex')
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
        if user.password != value:
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

        # Change the date_attended format: "YYYY-MM-DD HH:MM"
        date_attended = ret.get('date_attended')
        ret['date_attended'] = datetime_to_date_time(date_attended)

        return ret
    
    def create(self, validated_data):
        student = validated_data['student']

        # Add the date_attended time as now
        tz = pytz.timezone(settings.TIME_ZONE)
        now = datetime.now(tz=tz)
        
        # Attendance Check can't be repeated within 30min
        attendance =  Attendance.objects.filter(student=student).order_by('-date_attended')[:1]
        if attendance:
            attendance = attendance[0]
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

    def to_representation(self, instance):
        ret = super().to_representation(instance)

        """
        Hypothesis: The student attendance list should be searched within a month

        [INPUT]
        attendance_set: [ {id: 1, date_attended: '2023-08-09 12:53'}, {id: 13, date_attended: '2023-08-13 18:59'}]

        [OUTPUT]
        attendance_set: { 9 : [ {id: 1, date_attended: '12:53'} ], 13: [ {id: 13, date_attended: '18:59'} ] }
        """
        attendance_set = {}
        for attendnace in ret['attendance_set']:
            attendance_id, date_attended = attendnace['id'], attendnace['date_attended']
            date, time = date_attended.split()
            date = int(date[-2:])
            if date not in attendance_set:
                attendance_set[date] = []
            attendance_set[date].append({
                'id': attendance_id,
                'time': time,
            })

        ret['attendance_set'] = attendance_set
        return ret


class InbodyListSerializer(serializers.ListSerializer):    
    # Multiple create / update
    def update(self, instance, validated_data):
        # Instance mapping
        inbody_mapping = {inbody.id: inbody for inbody in instance}
        # Data mapping
        data_mapping = {}
        invalid_id = -1
        for item in validated_data:
            data_id = item.get('id')
            # Existing data => update
            if data_id:
                data_mapping[data_id] = item
            # Non Existing data => create
            else:
                data_mapping[invalid_id] = item
                invalid_id -= 1 # allocate invalid id temporarily

        """
        Handle DB integrity Error
        Return the detailed error message 
        """
        ret = []
        try:
            for inbody_id, data in data_mapping.items():
                inbody = inbody_mapping.get(inbody_id, None)
                # Create
                if inbody is None:
                    ret.append(self.child.create(data))
                # Update
                else: 
                    """
                    Restore the (grade, room, number) constraint, which is deactivated in the serializer
                    """
                    data['is_constraint_activated'] = True
                    ret.append(self.child.update(inbody, data))
                    
        except IntegrityError:
            test_date = data.get('test_date')
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'해당 학생에게 {test_date}의 인바디 기록이 이미 존재합니다'), 'db_integrity_error')
            
        return ret


class InbodySerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(required=False)    # For multiple update

    class Meta:
        list_serializer_class = InbodyListSerializer
        model = Inbody
        exclude = ['student', 'is_constraint_activated']

    # Error message for unique constraint
    def create(self, validated_data):
        try:
            return super().create(validated_data)
        except IntegrityError as e:
            code = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('해당 날짜에 인바디 기록이 이미 존재합니다'), f'db_{code}')

    def validate_test_date(self, value):
        if value > date.today():
            raise serializers.ValidationError(_('테스트 날짜가 오늘 이후일 수 없습니다'), 'invalid_birth_date')
        return value
    
    # Round data up to second decimal place
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
        fields = ['id', 'name', 'grade', 'room', 'number', 'inbody_set']
