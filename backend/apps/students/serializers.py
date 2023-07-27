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
        student_mapping = {student.id: student for student in instance}
        data_mapping = {item['id']: item for item in validated_data}

        ret = []
        # Handle DB integrity error
        try:
            for student_id, data in data_mapping.items():
                student = student_mapping.get(student_id, None)
                ret.append(self.child.update(student, data))
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{message}'), f'db_{code}')
            
        return ret


class StudentAuthSerializer(serializers.ModelSerializer):
    id = serializers.UUIDField(required=False)    # For multiple update

    class Meta:
        list_serializer_class = StudentListAuthSerializer
        model = Student
        fields = '__all__'

    # Since this is constraints, and the default validator isn't set.
    def validate_sex(self, value):
        if not value in (0, 1, 2, 9):
            raise serializers.ValidationError(_('Check the gender'), 'invalid_sex')
        return value
    
    def validate_birth_date(self, value):
        if value > date.today():
            raise serializers.ValidationError(_('The date of birth cannot be later than today'), 'invalid_birth_date')
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
            raise serializers.ValidationError(_('The existing password is incorrect'), 'invalid_old_password')
        return value
    
    def validate_new_password(self, value):
        if not re.compile(STUDENT_PASSWORD_REGEX).match(value):
            raise serializers.ValidationError(_('Password must be a 4 digit number'), 'invalid_old_password')
        return value
    
    def validate(self, data):
        new_password = data.get('new_password')
        confirm_password = data.get('confirm_password')

        if new_password != confirm_password:
            raise serializers.ValidationError({"confirm_password": _('The new passwords do not match')}, 'invalid_student_confirm_password')

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

    # def to_internal_value(self, data):
    #     ret = super().to_internal_value(data)
    #     tz = pytz.timezone(settings.TIME_ZONE)
    #     ret['date_attended'] = datetime.now(tz = tz )
    #     return ret

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
                raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'Attendance already checked {time_interval_min} minutes ago. Attendance check is available every 30 minutes'), 'too_fast_attendance')
            validated_data['date_attended'] = now

        return super().create(validated_data)


class StudentAttendanceSerializer(serializers.ModelSerializer):
    attendance_set = AttendanceSerializer(many=True, read_only=True)

    class Meta:
        model = Student
        fields = ['name', 'grade', 'room', 'number', 'attendance_set']


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
                if student is None:
                    ret.append(self.child.create(data))
                else:
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
        exclude = ['student']

    def create(self, validated_data):
        try:
            return super().create(validated_data)
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('Inbody history for that date already exists.'), f'db_{code}')

    def validate_test_date(self, value):
        if value > date.today():
            raise serializers.ValidationError(_('The date of birth cannot be later than today'), 'invalid_birth_date')
        return value


class StudentInbodySerializer(serializers.ModelSerializer):
    inbody_set = InbodySerializer(many=True, read_only=True)

    class Meta:
        model = Student
        fields = ['name', 'grade', 'room', 'number', 'inbody_set']
