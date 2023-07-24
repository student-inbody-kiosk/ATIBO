from rest_framework_simplejwt.authentication import JWTAuthentication
from rest_framework.authentication import BaseAuthentication

from atibo.utils.custom_token import decode


class StudentJWTAuthentication(BaseAuthentication):
    def authenticate(self, request):

        # Follow the JWT Authentication procedures
        jwtAuthentication = JWTAuthentication()
        header = jwtAuthentication.get_header(request)
        raw_token = jwtAuthentication.get_raw_token(header)

        # Don't raise Error
        # The follow-up authentications should be performed
        try:
            student= decode(raw_token)
        except:
            return None
        
        return Student(student), 'student' # (request.user, request.auth) 


"""
This class is made for providing `is_authenticated` property.
The `rest_framework.throttling.UserRateThrottle.get_cache_key()` requires `is_authenticated` property for the `request.user`

If there's any other way to provide `is_authenticated` property for `get_cache_key()`
This class can be substitued.
"""
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
