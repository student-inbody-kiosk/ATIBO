import re
import pytz
from datetime import datetime, timedelta

from django.utils import timezone
from django.conf import settings
from django.db.models import Q
from django.utils.translation import gettext_lazy as _

from rest_framework import status

from atibo.exceptions import DetailException
from atibo.regexes import KOREAN_NAME_REGEX, DATE_REGEX
from .models import Student

def get_student_object_from_path_variables(variables):
        grade = variables['grade']
        room = variables['room']
        number = variables['number']

        student = Student.objects.filter(grade=grade, room=room, number=number)

        # Find "the only one" student record
        if len(student) == 1:
            student = student[0]
        elif len(student) == 0:
            raise DetailException(status.HTTP_404_NOT_FOUND, _(f'There\'s no corresponding student'), 'sutdent_not_found')
        else:
            raise DetailException(status.HTTP_409_CONFLICT, _(f'There\'re more than one student corresponding to this information. Please contact your administrator'), 'sutdent_too_many')

        return student


def get_student_queryset_from_query_params(query_params):

        query_filter = Q()
        grade = query_params.get('grade')
        room = query_params.get('room')
        number = query_params.get('number')
        name = query_params.get('name')
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
            if not re.compile(KOREAN_NAME_REGEX).match(name):
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The name must be written in 2-5 Korean characters'), 'invalid_name')
            query_filter &= Q(name=name)

        queryset = Student.objects.filter(query_filter)
        return queryset


def get_date_from_path_variables(variables):
    start_date = variables['start_date']
    end_date = variables['end_date']

    tz = pytz.timezone(settings.TIME_ZONE)
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    start_date = timezone.make_aware(start_date, timezone=tz)
    end_date = datetime.strptime(end_date, "%Y-%m-%d")
    end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=1)

    return start_date, end_date

def get_date_from_query_params(query_params):

    start_date = query_params.get('start_date', '2023-01-01')
    end_date = query_params.get('end_date', datetime.today().strftime('%Y-%m-%d'))
    if not re.compile(DATE_REGEX).match(start_date) or not re.compile(DATE_REGEX).match(end_date):
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('Check the date format'), 'invalid_name')

    tz = pytz.timezone(settings.TIME_ZONE)
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    start_date = timezone.make_aware(start_date, timezone=tz)
    end_date = datetime.strptime(end_date, "%Y-%m-%d")
    end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=1)

    return start_date, end_date