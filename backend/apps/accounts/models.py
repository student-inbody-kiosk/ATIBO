import uuid

from django.db import models
from django.contrib.auth.models import AbstractUser
from django.core.validators import RegexValidator, MinLengthValidator
from django.utils.translation import gettext_lazy as _

from .regexes import username_regex, name_regex


class User(AbstractUser):
    # SubClass for ENUM type 'role' field
    class Role(models.TextChoices):
        ADMIN = "admin", _("administrator")
        USER = "user", _("normal user")

    # password : password validators are set in the settings.py
    # is_active : is_active field is inherited from AbstractUser
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    username = models.CharField(unique=True, max_length=20, validators=[RegexValidator(username_regex, _('The username must be 5 to 20 characters in combination with letters and numbers'), 'username_invalid')])
    name = models.CharField(max_length=5, validators=[RegexValidator(name_regex, _('The name must be written in 2-5 Korean characters'), 'name_invalid')])
    email = models.EmailField(unique=True)
    role = models.CharField(max_length=5, choices=Role.choices, default=Role.USER)  # enum
    comment = models.CharField(max_length=100, validators=[MinLengthValidator(10, _('The comment length must be longer than 10'))])
    refresh_token = models.TextField(blank=True, default='')


# CREATE TABLE `accounts_user` (`password` varchar(128) NOT NULL, `last_login` datetime(6) NULL, `is_superuser` bool NOT NULL, `first_name` varchar(150) NOT NULL, `last_name` varchar(150) NOT NULL, `is_staff` bool NOT NULL, `is_active` bool NOT NULL, `date_joined` datetime(6) NOT NULL, `id` char(32) NOT NULL PRIMARY KEY, `username` varchar(20) NOT NULL UNIQUE, `email` varchar(254) NOT NULL UNIQUE, `name` varchar(5) NOT NULL UNIQUE, `role` varchar(5) NOT NULL, `comment` varchar(100) NOT NULL, `refresh_token` longtext NOT NULL);
# CREATE TABLE `accounts_user_groups` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `user_id` char(32) NOT NULL, `group_id` integer NOT NULL);
# CREATE TABLE `accounts_user_user_permissions` (`id` bigint AUTO_INCREMENT NOT NULL PRIMARY KEY, `user_id` char(32) NOT NULL, `permission_id` integer NOT NULL);
# ALTER TABLE `accounts_user_groups` ADD CONSTRAINT `accounts_user_groups_user_id_group_id_59c0b32f_uniq` UNIQUE (`user_id`, `group_id`);
# ALTER TABLE `accounts_user_groups` ADD CONSTRAINT `accounts_user_groups_user_id_52b62117_fk_accounts_user_id` FOREIGN KEY (`user_id`) REFERENCES `accounts_user` (`id`);
# ALTER TABLE `accounts_user_groups` ADD CONSTRAINT `accounts_user_groups_group_id_bd11a704_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`);
# ALTER TABLE `accounts_user_user_permissions` ADD CONSTRAINT `accounts_user_user_permi_user_id_permission_id_2ab516c2_uniq` UNIQUE (`user_id`, `permission_id`);
# ALTER TABLE `accounts_user_user_permissions` ADD CONSTRAINT `accounts_user_user_p_user_id_e4f0a161_fk_accounts_` FOREIGN KEY (`user_id`) REFERENCES `accounts_user` (`id`);
# ALTER TABLE `accounts_user_user_permissions` ADD CONSTRAINT `accounts_user_user_p_permission_id_113bb443_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`);