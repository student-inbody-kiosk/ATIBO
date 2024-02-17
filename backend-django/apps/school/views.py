from django.utils.translation import gettext_lazy as _

from rest_framework.generics import RetrieveUpdateAPIView

from atibo.permissions import ReadOnly, IsAdminUser
from .serializers import SchoolSerializer
from .models import School


"""
get: get school info
put: update school iinfo
"""
class SchoolAPIView(RetrieveUpdateAPIView):
    http_method_names = ["get", "put"]
    permission_classes = [ReadOnly |  IsAdminUser]
    serializer_class = SchoolSerializer

    # The only record
    def get_object(self):
        return School.objects.get(id=1)
    
    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'get':
            return []
        else:
            return [auth() for auth in self.authentication_classes] # JWTAuthentication