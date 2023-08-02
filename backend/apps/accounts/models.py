import uuid

from django.db import models
from django.contrib.auth.models import AbstractUser
from django.core.validators import RegexValidator, MinLengthValidator
from django.utils.translation import gettext_lazy as _

from atibo.regexes import USERNAME_REGEX, KOREAN_NAME_REGEX


class User(AbstractUser):
    # SubClass for ENUM type 'role' field
    class Role(models.TextChoices):
        ADMIN = "admin", _("administrator")
        USER = "user", _("normal user")

    # password : password validators are set in the settings.py
    # is_active : is_active field is inherited from AbstractUser
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    username = models.CharField(unique=True, editable=False, max_length=20, validators=[RegexValidator(USERNAME_REGEX, _('The username must be 5 to 20 characters in combination with letters and numbers'), 'username_invalid')])
    name = models.CharField(max_length=5, validators=[RegexValidator(KOREAN_NAME_REGEX, _('The name must be written in 2-5 Korean characters'), 'name_invalid')])
    email = models.EmailField(unique=True)
    role = models.CharField(max_length=5, editable=False, choices=Role.choices, default=Role.USER)  # enum
    comment = models.CharField(max_length=100, validators=[MinLengthValidator(10, _('The comment length must be longer than 10'))])
    refresh_token = models.TextField(blank=True, default='')