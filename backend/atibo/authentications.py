import jwt

from django.conf import settings
from django.contrib.auth.models import User

from rest_framework_simplejwt.authentication import JWTAuthentication
from rest_framework import authentication
from rest_framework import exceptions

from atibo.exceptions import DetailException

# Header encoding (see RFC5987)
HTTP_HEADER_ENCODING = 'iso-8859-1'

class StudentJWTAuthentication(authentication.BaseAuthentication):
    def authenticate(self, request):

        # Follow the JWT Authentication
        jwtAuthentication = JWTAuthentication()
        header = jwtAuthentication.get_header(request)
        raw_token = jwtAuthentication.get_raw_token(header)

        try:
            student= jwt.decode(raw_token, settings.SECRET_KEY, algorithms=['HS256'])
        except jwt.ExpiredSignatureError:
            return None
        except jwt.InvalidTokenError:
            return None

        # Which has .is_authenticated proeprtty
        # rest_framework.throttling.UserRateThrottle
        student = Student(student)

        return student, 'student' # (.user, .auth)
    

class Student():

    def __init__(self, student):
        self.__id = student.get('id')
        self.__name = student.get('name')
        self.__grade = student.get('grade')
        self.__room = student.get('room')
        self.__number = student.get('number')

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name
    
    @property
    def grade(self):
        return self.__grade
    
    @property
    def room(self):
        return self.__room
    
    @property
    def number(self):
        return self.__number

    @property
    def is_authenticated(self):
        return False
