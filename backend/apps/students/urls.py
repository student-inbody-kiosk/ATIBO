from django.urls import path

from rest_framework import routers

from .views import StudentAuthAPIView, StudentCheckAPIView, StudentDetailAPIView, StudentLoginAPIView, StudentPasswordChangeAPIView

app_name = 'students'

# router = routers.SimpleRouter()
# router.register(r'admin', AdminViewSet)

urlpatterns = [
    path('', StudentAuthAPIView.as_view(), name='students'),
    path('check/<int:grade>/<int:room>/<int:number>', StudentCheckAPIView.as_view(), name='student_check'),
    path('login', StudentLoginAPIView.as_view(), name='student_login'),
    path('<int:grade>/<int:room>/<int:number>', StudentDetailAPIView.as_view(), name='student_detail'),
    # path('password/change', StudentPasswordChangeAPIView.as_view(), name='stduent_password_change'),
]

# urlpatterns += router.urls
