from django.db.models import Q
import re

from django.utils.translation import gettext_lazy as _

from rest_framework import status

from atibo.exceptions import DetailException
from atibo.regexes import korean_name_regex
from .models import Student

def get_student_grade_room_number(grade, room, number):

        student = Student.objects.filter(grade=grade, room=room, number=number)

        # Find "the only one" student record
        if len(student) == 1:
            student = student[0]
        elif len(student) == 0:
            raise DetailException(status.HTTP_404_NOT_FOUND, _(f'There\'s no corresponding student'), 'sutdent_not_found')
        else:
            raise DetailException(status.HTTP_409_CONFLICT, _(f'There\'re more than one student corresponding to this information. Please contact your administrator'), 'sutdent_too_many')

        return student


def get_student_queryset_from_query_params(request):

        query_filter = Q()
        grade = request.query_params.get('grade')
        room = request.query_params.get('room')
        number = request.query_params.get('number')
        name = request.query_params.get('name')
        if grade:
            if not grade.isdecimal():
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The grade must be a numeric value from 1 to 9'), 'invalid_grade')
            query_filter &= Q(grade=grade)
        if room:
            if not room.isdecimal():
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The room must be a numeric value from 1 to 99'), 'invalid_room')
            query_filter &= Q(room=room)
        if number:
            if not number.isdecimal():
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The number must be a numeric value from 1 to 100'), 'invalid_number')
            query_filter &= Q(number=number)
        if name:
            if not re.compile(korean_name_regex).match(name):
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The name must be written in 2-5 Korean characters'), 'invalid_name')
            query_filter &= Q(name=name)

        queryset = Student.objects.filter(query_filter)
        return queryset