# Generated by Django 4.2.3 on 2023-08-06 08:02

import django.core.validators
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('school', '0003_alter_school_logo_image'),
    ]

    operations = [
        migrations.AlterField(
            model_name='school',
            name='name',
            field=models.CharField(max_length=25, validators=[django.core.validators.MinLengthValidator(2, '학교 이름은 2글자 이상으로 작성해주세요')]),
        ),
    ]