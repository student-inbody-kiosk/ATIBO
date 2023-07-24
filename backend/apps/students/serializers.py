from django.db.utils import IntegrityError
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from rest_framework import status, serializers

from atibo.exceptions import DetailException
from .models import Student


class StudentListAuthSerializer(serializers.ListSerializer):
    # Mulltiple Create
    def create(self, validated_data):
        students = [Student(**item) for item in validated_data]
        # Handle db integrity error
        try:
            instance = Student.objects.bulk_create(students)
        except IntegrityError as e:
            code, message = e.args
            raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{message}'), f'db_{code}')
        return instance
    
    # Multiple Update
    def update(self, instance, validated_data):
        # Maps for id->instance and id->data item.
        student_mapping = {student.id: student for student in instance}
        data_mapping = {item['id']: item for item in validated_data}

        # Handle db integrity error
        ret = []
        for student_id, data in data_mapping.items():
            student = student_mapping.get(student_id, None)
            try:
                ret.append(self.child.update(student, data))
            except IntegrityError as e:
                code, message = e.args
                raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'{message}'), f'db_{code}')
        return ret


class StudentAuthSerializer(serializers.ModelSerializer):
    id = serializers.UUIDField()    # For multiple update

    class Meta:
        list_serializer_class = StudentListAuthSerializer
        model = Student
        fields = '__all__'

    # Since this is constraints, The default validator isn't set.
    def validate_sex(self, value):
        if not value in (0, 1, 2, 9):
            raise serializers.ValidationError(_('Check the gender'), 'invalid_sex')
        return value


class StudentCheckSerializer(serializers.ModelSerializer):

    class Meta:
        model = Student
        fields = ['id', 'name', 'grade', 'room', 'number']


class StudentLoginSerializer(serializers.ModelSerializer):
    id = serializers.UUIDField()    # For multiple update

    class Meta:
        model = Student
        fields = ['id', 'password']
        extra_kwargs = {
            'id': {'write_only': True},
            'password': {'write_only': True}
        }

    def validate(self, data):
        id = data.get('id')
        password = data.get('password')

        student = get_object_or_404(Student, id = id)

        if student.password != password:
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('The password is incorrect'), 'invalid_password')
        data['student'] = student

        return data

class StudentDetailSerializer(serializers.ModelSerializer):

    class Meta:
        model = Student
        fields = ['id', 'name', 'grade', 'room', 'number', 'sex', 'birth_date']

