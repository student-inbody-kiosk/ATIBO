from django.db.utils import IntegrityError
from django.utils.translation import gettext_lazy as _

from rest_framework import status, serializers

from atibo.exceptions import DetailException
from .models import Student


class StudentListSerializer(serializers.ListSerializer):
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
            except:
                grade = data['grade']
                room = data['room']
                number = data['number']
                name = data['name']
                raise DetailException(status.HTTP_404_NOT_FOUND, _(f'Not found the student info of {grade}-{room}-{number} {name}'), 'sutdent_not_found')
        return ret


class StudentSerializer(serializers.ModelSerializer):
    id = serializers.UUIDField()    # For multiple update

    class Meta:
        list_serializer_class = StudentListSerializer
        model = Student
        fields = '__all__'

    # Since this is constraints, The default validator isn't set.
    def validate_sex(self, value):
        if not value in (0, 1, 2, 9):
            raise serializers.ValidationError(_('Check the gender'), 'invalid_sex')
        return value
