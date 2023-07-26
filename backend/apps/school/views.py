from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from rest_framework.generics import RetrieveUpdateAPIView
from rest_framework.permissions import IsAuthenticated

from atibo.permissions import ReadOnly, IsAdminUser
from .serializers import SchoolSerializer
from .models import School


class SchoolAPIView(RetrieveUpdateAPIView):
    http_method_names = ["get", "put"]
    permission_classes = [ReadOnly | IsAdminUser]
    serializer_class = SchoolSerializer

    def get_object(self):
        return School.objects.get(id=1)
    
    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'get':
            return []  # Empty list for authentication on get  method
        else:
            return [auth() for auth in self.authentication_classes]