# Generated by Django 4.2.3 on 2023-07-25 02:11

import atibo.fields
import django.core.validators
from django.db import migrations, models
import uuid


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Student',
            fields=[
                ('id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=5, validators=[django.core.validators.RegexValidator('^[가-힣]{2,5}$', 'The name must be written in 2-5 Korean characters', 'name_invalid')])),
                ('grade', atibo.fields.TinyIntegerField(validators=[django.core.validators.MinValueValidator(1, 'The grade must be greater than 0'), django.core.validators.MaxValueValidator(9, 'The grade must be less than 10')])),
                ('room', atibo.fields.TinyIntegerField(validators=[django.core.validators.MinValueValidator(1, 'The room must be greater than 0'), django.core.validators.MaxValueValidator(99, 'The room must be less than 100')])),
                ('number', atibo.fields.TinyIntegerField(validators=[django.core.validators.MinValueValidator(1, 'The number must be greater than 0'), django.core.validators.MaxValueValidator(100, 'The number must be less or equal than 100')])),
                ('sex', atibo.fields.TinyIntegerField()),
                ('password', models.CharField(default='0000', max_length=4, validators=[django.core.validators.MinLengthValidator(4, 'The password length must be 4'), django.core.validators.RegexValidator('^\\d{4}$', 'The password must be a numeric value', 'student_password_invalid')])),
                ('birth_date', models.DateField()),
                ('is_authenticated', models.BooleanField(default=False, editable=False)),
            ],
            options={
                'indexes': [models.Index(fields=['grade', 'room', 'number'], name='grade_room_number_idx')],
            },
        ),
        migrations.AddConstraint(
            model_name='student',
            constraint=models.UniqueConstraint(fields=('grade', 'room', 'number'), name='unique_grade_room_number'),
        ),
        migrations.AddConstraint(
            model_name='student',
            constraint=models.CheckConstraint(check=models.Q(('sex__in', [0, 1, 2, 9])), name='valid_sex_values'),
        ),
    ]