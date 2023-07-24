import jwt
from datetime import timedelta

from django.conf import settings
from django.utils import timezone

CUSTOM_TOKEN_ALGORITHM = 'HS256'

def encode(payload, days=0, seconds=0, minutes=0, hours=0, weeks=0):
    # Set expiration in the data entity
    exp = timezone.now() + timedelta(days=days, seconds=seconds, minutes=minutes, hours=hours, weeks=weeks)
    payload['exp'] = exp

    return jwt.encode(payload, settings.SECRET_KEY, algorithm=CUSTOM_TOKEN_ALGORITHM)


def decode(token):
    return jwt.decode(token, settings.SECRET_KEY, algorithms=[CUSTOM_TOKEN_ALGORITHM])