import re

from django.core.exceptions import ValidationError
from django.utils.translation import gettext_lazy as _


class NumberValidator(object):
    def validate(self, password, user=None):
        if not re.findall('\d', password):
            raise ValidationError(
                _("비밀번호는 적어도 하나의 숫자를 포함해야 합니다"),
                code='password_no_number',
            )

    def get_help_text(self):
        return _(
            "비밀번호는 적어도 하나의 숫자를 포함해야 합니다"
        )


class SpecialCharValidator(object):
    def validate(self, password, user=None):
        if not re.findall('[()[\]{}|\~!@#$%^&*_\-+=;:,<>./?]', password):
            raise ValidationError(
                _("비밀번호는 적어도 하나의 특수문자를 포함해야 합니다: " +
                  "()[]{}|\`~!@#$%^&*_-+=;:,<>./?"),
                code='password_no_symbol',
            )

    def get_help_text(self):
        return _(
            "비밀번호는 적어도 하나의 특수문자를 포함해야 합니다: " +
            "()[]{}|\~!@#$%^&*_-+=;:,<>./?"
        )