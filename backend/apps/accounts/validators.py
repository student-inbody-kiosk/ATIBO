import re

from django.core.exceptions import ValidationError
from django.utils.translation import gettext_lazy as _


class NumberValidator(object):
    def validate(self, password, user=None):
        if not re.findall('\d', password):
            raise ValidationError(
                _("The password must contain at least 1 digit, 0-9."),
                code='password_no_number',
            )

    def get_help_text(self):
        return _(
            "Your password must contain at least 1 digit, 0-9."
        )
    
class SpecialCharValidator(object):
    def validate(self, password, user=None):
        if not re.findall('[()[\]{}|\~!@#$%^&*_\-+=;:,<>./?]', password):
            raise ValidationError(
                _("The password must contain at least 1 symbol: " +
                  "()[]{}|\`~!@#$%^&*_-+=;:,<>./?"),
                code='password_no_symbol',
            )

    def get_help_text(self):
        return _(
            "Your password must contain at least 1 symbol: " +
            "()[]{}|\~!@#$%^&*_-+=;:,<>./?"
        )