from django.utils.translation import gettext_lazy as _

from rest_framework.generics import RetrieveUpdateAPIView

from atibo.permissions import ReadOnly, IsAdminUser
from .serializers import SchoolSerializer
from .models import School

import time

from atibo.exceptions import DetailException
from rest_framework import status


class SchoolAPIView(RetrieveUpdateAPIView):
    http_method_names = ["get", "put"]
    permission_classes = [ReadOnly | IsAdminUser]
    serializer_class = SchoolSerializer

    def get(self, request, *args, **kwargs):
        time.sleep(3) 
        raise DetailException(status.HTTP_400_BAD_REQUEST, _('The grade must be a numeric value from 1 to 9'), 'invalid_grade')
        return self.retrieve(request, *args, **kwargs)

    def get_object(self):

        return School.objects.get(id=1)
    
    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'get':
            return []  # Empty list for authentication on get  method
        else:
            return [auth() for auth in self.authentication_classes]