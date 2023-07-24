from django.urls import path

from rest_framework import routers

from .views import StudentAPIView, StudentLoginAPIView, StudentPasswordChangeAPIView

app_name = 'students'

# router = routers.SimpleRouter()
# router.register(r'admin', AdminViewSet)

urlpatterns = [
    path('', StudentAPIView.as_view(), name='students'),
    path('login', StudentLoginAPIView.as_view(), name='student_login'),
    path('password/change', StudentPasswordChangeAPIView.as_view(), name='stduent_password_change'),
]

# urlpatterns += router.urls
