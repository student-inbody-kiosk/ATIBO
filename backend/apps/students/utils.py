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
    # Extract values from path variables
    grade = variables['grade']
    room = variables['room']
    number = variables['number']

    # Search object
    student = Student.objects.filter(grade=grade, room=room, number=number)

    # Confirm the object is only one
    if len(student) == 1:
        student = student[0]
    elif len(student) == 0:
        raise DetailException(status.HTTP_404_NOT_FOUND, _(f'There\'s no corresponding student'), 'sutdent_not_found')
    else:
        raise DetailException(status.HTTP_409_CONFLICT, _(f'There\'re more than one student corresponding to this information. Please contact your administrator'), 'sutdent_too_many')

    return student


def get_student_queryset_from_query_params(query_params):
    # Extract values from query params
    grade = query_params.get('grade')
    room = query_params.get('room')
    number = query_params.get('number')
    name = query_params.get('name')
    
    # Make dynamic query filter
    query_filter = Q()
    params_valid = False
    if grade and grade.isdigit() and 0 < int(grade) < 10:
        params_valid = True
        query_filter &= Q(grade=int(grade))
        # raise DetailException(status.HTTP_400_BAD_REQUEST, _('The grade must be a numeric value from 1 to 9'), 'invalid_grade')
    if room and room.isdigit() and 0 < int(room) < 100:
        params_valid = True
        query_filter &= Q(room=int(room))
        # raise DetailException(status.HTTP_400_BAD_REQUEST, _('The room must be a numeric value from 1 to 99'), 'invalid_room')
    if number and number.isdigit() and 0 < int(number) < 101:
        params_valid = True
        query_filter &= Q(number=int(number))
        # raise DetailException(status.HTTP_400_BAD_REQUEST, _('The number must be a numeric value from 1 to 100'), 'invalid_number')
    if name and re.compile(KOREAN_NAME_REGEX).match(name):
        query_filter &= Q(name=name)
        # raise DetailException(status.HTTP_400_BAD_REQUEST, _('The name must be written in 2-5 Korean characters'), 'invalid_name')

    # For prevent large data transferring, constrain the params
    if not params_valid:
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('You must enter at least one of the following values: Grade, Class, or Number'), 'invalid_params')

    return Student.objects.filter(query_filter)


def get_date_from_path_variables(variables, limit_period_days = 62):
    # Extract values from path variables
    start_date = variables['start_date']
    end_date = variables['end_date']

    # Customize the value including adding the timezone
    tz = pytz.timezone(settings.TIME_ZONE)
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    start_date = timezone.make_aware(start_date, timezone=tz)
    end_date = datetime.strptime(end_date, "%Y-%m-%d")
    end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=1)

    print('path', start_date, end_date, timedelta(days=limit_period_days))
    # Limit the period
    if end_date - start_date > timedelta(days=limit_period_days):
        raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'Set the period within {limit_period_days} days'), 'invalid_period')
    

    return start_date, end_date

def get_date_from_query_params(query_params, limit_period_days = 730):
    # Extract values from query params
    start_date = query_params.get('start_date', '2023-01-01')
    end_date = query_params.get('end_date', datetime.today().strftime('%Y-%m-%d'))

    if not re.compile(DATE_REGEX).match(start_date) or not re.compile(DATE_REGEX).match(end_date):
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('Check the date format'), 'invalid_name')

    # Customize the value including adding the timezone
    tz = pytz.timezone(settings.TIME_ZONE)
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    start_date = timezone.make_aware(start_date, timezone=tz)
    end_date = datetime.strptime(end_date, "%Y-%m-%d")
    end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=limit_period_days)

    # if end_date - start_date > timedelta(days=limit_period_days):
    #     raise DetailException(status.HTTP_400_BAD_REQUEST, _('Set the period within two months'), 'invalid_period')

    return start_date, end_date