from django.db import models
from django.utils.translation import gettext_lazy as _
from django.core.validators import MinLengthValidator

from ckeditor.fields import RichTextField


# Gym equipment image directory
def gym_equipment_directory_path(instance, filename):
    return f'gym/{instance.equipment.name}/{filename}'

class Equipment(models.Model):
    name = models.CharField(max_length=30, validators=[MinLengthValidator(2, _('이름은 2글자 이상으로 작성해주세요'))])
    description = RichTextField(blank=True)

class Image(models.Model):
    equipment = models.ForeignKey(Equipment, on_delete=models.CASCADE)
    image = models.ImageField(blank=True, upload_to=gym_equipment_directory_path, max_length=255)
