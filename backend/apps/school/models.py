from django.conf import settings
from django.db import models
from django.core.validators import MinLengthValidator
from django.utils.translation import gettext_lazy as _


DEFAULT_LOGO_IMAGE_PATH = 'school/default-logo-image.png'   # relative to 

def school_logo_directory_path(instance, filename):
    return f'school/logo/{filename}'

class School(models.Model):
    class Only(models.IntegerChoices):
        SCHOOL = 1, _("school")

    id = models.IntegerField(primary_key=True, editable=False,  choices=Only.choices, default=Only.SCHOOL) # the only record
    name = models.CharField(max_length=25, validators=[MinLengthValidator(2, _('학교 이름은 2글자 이상으로 작성해주세요'))])
    logo_image = models.ImageField(null=True, upload_to=school_logo_directory_path, max_length=255)  
