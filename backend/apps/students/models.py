from django.db import models
from django.core.validators import RegexValidator, MinValueValidator, MaxValueValidator, MinLengthValidator
from django.utils.translation import gettext_lazy as _
from atibo.fields import TinyIntegerField

class Student(models.Model):
    name = models.CharField(max_length=5, validators=[RegexValidator(r'^[가-힣]{2,5}$', _('The name must be written in 2-5 Korean characters'), 'name_invalid')])
    grade = TinyIntegerField(validators=[MinValueValidator(1, _('The grade must be greater than 0')), MaxValueValidator(9,  _('The grade must be less than 10'))])
    room = TinyIntegerField(validators=[MinValueValidator(1, _('The room must be greater than 0')), MaxValueValidator(99,  _('The room must be less than 100'))])
    number = TinyIntegerField(validators=[MinValueValidator(1, _('The number must be greater than 0')), MaxValueValidator(100,  _('The number must be less or equal than 100'))])
    sex = TinyIntegerField()
    password = models.CharField(max_length=4, validators=[MinLengthValidator(4, _('The password length must be 4')), RegexValidator(r'^\d{4}$', _('The password must be a numeric value'), 'student_password_invalid')])
    birth_date = models.DateField()

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=['grade', 'room', 'number'], name='unique_grade_room_number'),   
            models.CheckConstraint(check=models.Q(sex__in=[0, 1, 2, 9]), name='valid_sex_values'),  # https://en.wikipedia.org/wiki/ISO/IEC_5218
        ]
        indexes = [
            models.Index(fields=['grade', 'room', 'number'], name='grade_room_number_idx')
        ]