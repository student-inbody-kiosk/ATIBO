# Generated by Django 4.2.3 on 2023-07-27 12:36

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('gym', '0003_alter_equipment_name_alter_image_image'),
    ]

    operations = [
        migrations.RemoveIndex(
            model_name='image',
            name='image_equipment_idx',
        ),
    ]