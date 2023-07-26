from django.db import models
from django.core.validators import RegexValidator
from django.utils.translation import gettext_lazy as _

from ckeditor_uploader.fields import RichTextUploadingField


class Equipment(models.Model):
    name = models.CharField(max_length=30)
    description = RichTextUploadingField(blank=True)