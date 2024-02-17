from django.urls import path

from .views import SchoolAPIView


app_name = 'school'

urlpatterns = [
    path('', SchoolAPIView.as_view(), name='school'),
]