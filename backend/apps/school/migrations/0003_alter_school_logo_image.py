# Generated by Django 4.2.3 on 2023-07-27 04:33

from django.db import migrations, models
import school.models


class Migration(migrations.Migration):

    dependencies = [
        ('school', '0002_alter_school_logo_image_alter_school_name'),
    ]

    operations = [
        migrations.AlterField(
            model_name='school',
            name='logo_image',
            field=models.ImageField(max_length=255, null=True, upload_to=school.models.school_logo_directory_path),
        ),
    ]