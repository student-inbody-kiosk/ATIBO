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


STR_DATE_FORMAT = '%Y-%m-%d'

def get_student_object_from_path_variables(variables):
    # Extract values from path variables
    grade = variables['grade']
    room = variables['room']
    number = variables['number']

    # Search object
    student = Student.objects.filter(grade=grade, room=room, number=number)

    # Confirm the object is only one
    num_student = len(student) 
    if num_student == 1:
        return student[0]
    elif num_student == 0:
        raise DetailException(status.HTTP_404_NOT_FOUND, _('해당 학년, 반, 번호의 학생이 없습니다'), 'sutdent_not_found')
    else:
        raise DetailException(status.HTTP_409_CONFLICT, _('해당 학년, 반, 번호의 학생이 여러 명입니다. 관리자에게 문의해주세요'), 'sutdent_too_many')


def get_student_queryset_from_query_params(query_params):
    # Extract values from query params
    grade = query_params.get('grade')
    room = query_params.get('room')
    number = query_params.get('number')
    name = query_params.get('name')

    try:
        grade, room, number = int(grade), int(room), int(number)
    except ValueError:
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('학년, 반, 번호는 정수로 입력해주세요'), 'invalid_grade')

    # Make dynamic query filter
    query_filter = Q()
    params_valid = False
    if grade:
        if 0 < grade < 10:
            params_valid = True
            query_filter &= Q(grade=grade)
        else:
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('학년은 1~9 사이의 값으로 입력해주세요'), 'invalid_grade')
    if room:
        if 0 < room < 100:
            params_valid = True
            query_filter &= Q(room=room)
        else:
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('반은 1~99 사이의 값으로 입력해주세요'), 'invalid_room')
    if number:
        if 0 < number < 101:
            params_valid = True
            query_filter &= Q(number=number)
        else:
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('번호는 1~100 사이의 값으로 입력해주세요'), 'invalid_number')
    if name: 
        if re.compile(KOREAN_NAME_REGEX).match(name):
            params_valid = True
            query_filter &= Q(name=name)
        else:
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('이름은 2~5자의 한글로 입력해주세요'), 'invalid_name')

    # For prevent large data transferring, at least one params should be entered
    if not params_valid:
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('학년, 반, 번호, 이름 중 적어도 하나의 값을 입력해주세요'), 'invalid_params')

    # Return the dynamic query
    return Student.objects.filter(query_filter).order_by('grade', 'room', 'number')


def get_date_from_path_variables(variables, limit_period_days = 62):
    # Extract values from path variables
    start_date = variables['start_date']
    end_date = variables['end_date']

    # Customize the value including timezone
    tz = pytz.timezone(settings.TIME_ZONE)
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    start_date = timezone.make_aware(start_date, timezone=tz)
    end_date = datetime.strptime(end_date, "%Y-%m-%d")
    end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=1) # Add one day because the default time is 00:00:00 

    # Limit the period
    if end_date - start_date > timedelta(days=limit_period_days):
        raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'기간은 {limit_period_days}일 이내로 설정해주세요'), 'invalid_period')
    
    # Filter the invalid period
    if start_date > end_date:
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('시작일이 종료일보다 클 수 없습니다'), 'invalid_period')
    
    return start_date, end_date

def get_date_from_month_in_path_variables(variables):
    # Extract values from path variables
    year_month = variables['year_month']

    # Create start_date & end_date from the month
    tz = pytz.timezone(settings.TIME_ZONE)

    current_month = datetime.strptime(year_month, '%Y-%m')
    start_date = current_month.replace(day=1)
    start_date = timezone.make_aware(start_date, timezone=tz)

    next_month = current_month + timedelta(days=31)
    end_date = next_month.replace(day=1)    # Add one day because the default time is 00:00:00 
    end_date = timezone.make_aware(end_date, timezone=tz)

    return start_date, end_date

def get_date_from_query_params(query_params, limit_period_days = 730):
    # Extract values from query params
    start_date = query_params.get('start_date', '2023-01-01')
    end_date = query_params.get('end_date', datetime.today().strftime(STR_DATE_FORMAT))

    # Validate the date parameters
    if not re.compile(DATE_REGEX).match(start_date) or not re.compile(DATE_REGEX).match(end_date):
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('날짜 형식을 확인해주세요'), 'invalid_name')

    # Customize the value including adding the timezone
    tz = pytz.timezone(settings.TIME_ZONE)
    start_date = datetime.strptime(start_date, "%Y-%m-%d")
    start_date = timezone.make_aware(start_date, timezone=tz)
    end_date = datetime.strptime(end_date, "%Y-%m-%d")
    end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=1)   # Add one day because the default time is 00:00:00 

    # Limit the search time period
    if end_date - start_date > timedelta(days=limit_period_days):
        raise DetailException(status.HTTP_400_BAD_REQUEST, _(f'기간은 {limit_period_days}일 이내로 설정해주세요'), 'invalid_period')
    
    # Filter the invalid period
    if start_date > end_date:
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('시작일이 종료일보다 클 수 없습니다'), 'invalid_period')

    return start_date, end_date