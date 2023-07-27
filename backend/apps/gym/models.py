from django.db import models
from django.utils.translation import gettext_lazy as _
from django.core.validators import MinLengthValidator

from ckeditor.fields import RichTextField


def gym_equipment_directory_path(instance, filename):
    return f'gym/{instance.equipment.name}/{filename}'

class Equipment(models.Model):
    name = models.CharField(max_length=30, validators=[MinLengthValidator(2, _('The name length must be longer than 2'))])
    description = RichTextField(blank=True)

class Image(models.Model):
    equipment = models.ForeignKey(Equipment, on_delete=models.CASCADE)
    image = models.ImageField(blank=True, upload_to=gym_equipment_directory_path, max_length=255)