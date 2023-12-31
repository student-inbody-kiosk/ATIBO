from rest_framework_simplejwt.authentication import JWTAuthentication
from rest_framework.authentication import BaseAuthentication

from atibo.utils.custom_token import decode

from students.models import Student

class StudentJWTAuthentication(BaseAuthentication):
    def authenticate(self, request):

        # Follow the JWT Authentication procedures
        jwtAuthentication = JWTAuthentication()
        header = jwtAuthentication.get_header(request)

        # Don't raise Error
        # The follow-up authentications should be performed
        try:
            raw_token = jwtAuthentication.get_raw_token(header)
            student = decode(raw_token)
            student = Student.objects.get(id=student['id'])
        except Exception:
            return None
        
        return student, 'student' # (request.user, request.auth) 