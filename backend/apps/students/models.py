import uuid

from django.db import models
from django.core.validators import RegexValidator, MinValueValidator, MaxValueValidator, MinLengthValidator
from django.utils.translation import gettext_lazy as _

from atibo.fields import TinyIntegerField
from atibo.regexes import KOREAN_NAME_REGEX, STUDENT_PASSWORD_REGEX


class Student(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=25, validators=[RegexValidator(KOREAN_NAME_REGEX, _('이름은 2~5자의 한글로 작성해주세요'), 'name_invalid')])
    grade = TinyIntegerField(validators=[MinValueValidator(1, _('학년은 0보다 커야 합니다')), MaxValueValidator(9,  _('학년은 10보다 작아야 합니다'))])
    room = TinyIntegerField(validators=[MinValueValidator(1, _('반은 0보다 커야 합니다')), MaxValueValidator(99,  _('반은 100보다 작아야 합니다'))])
    number = TinyIntegerField(validators=[MinValueValidator(1, _('번호는 0보다 커야 합니다')), MaxValueValidator(100,  _('번호는 100 이하여야 합니다'))])
    sex = TinyIntegerField()
    password = models.CharField(default='0000', max_length=4, validators=[MinLengthValidator(4, _('비밀번호의 길이는 4자여야 합니다')), RegexValidator(STUDENT_PASSWORD_REGEX, _('비밀번호는 숫자로만 이뤄져야 합니다'), 'student_password_invalid')])
    birth_date = models.DateField()
    is_authenticated = models.BooleanField(default=False, editable=False)   # Just for the 'is_authenticated' property, which is required by 'rest_framework.throttling.UserRateThrottle.get_cache_key()'

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=['grade', 'room', 'number'], name='unique_grade_room_number'),   
            models.CheckConstraint(check=models.Q(sex__in=[0, 1, 2, 9]), name='valid_sex_values'),  # https://en.wikipedia.org/wiki/ISO/IEC_5218
        ]
        # Django Model Index
        # https://docs.djangoproject.com/en/4.2/ref/models/options/#indexes
        indexes = [
            models.Index(fields=['grade', 'room', 'number'], name='grade_room_number_idx'),
        ]


class Attendance(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    date_attended = models.DateTimeField(auto_now_add=False)

    class Meta:
        indexes = [
            models.Index(fields=['student', 'date_attended'], name='attendance_student_idx'),
        ]


class Inbody(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    test_date = models.DateField()
    weight = models.FloatField()
    percent_body_fat = models.FloatField()
    skeletal_muscle_mass = models.FloatField()
    height = models.FloatField(null=True)
    age = TinyIntegerField(null=True, validators=[MinValueValidator(1, _('나이는 0보다 커야 합니다')), MaxValueValidator(127,  _('나이는 128보다 작아야 합니다'))])
    total_body_water = models.FloatField(null=True)
    protein = models.FloatField(null=True)
    minerals = models.FloatField(null=True)
    body_fat_mass = models.FloatField(null=True)
    body_mass_index = models.FloatField(null=True)
    score = TinyIntegerField(null=True, validators=[MinValueValidator(1, _('인바디 점수는 0보다 커야 합니다')), MaxValueValidator(100,  _('인바디 점수는 100이하여야 합니다'))])

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=['student', 'test_date'], name='test_per_day'),   
        ]
        indexes = [
            models.Index(fields=['student', 'test_date'], name='student_test_date_idx'),
        ]