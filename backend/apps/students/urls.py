from django.urls import path, re_path

from rest_framework import routers

from .views import StudentAuthAPIView, StudentCheckAPIView, StudentDetailAPIView, StudentLoginAPIView, StudentPasswordChangeAPIView, AttendanceCheckAPIView, StudentAttendanceAPIView

app_name = 'students'

# router = routers.SimpleRouter()
# router.register(r'admin', AdminViewSet)

urlpatterns = [
    path('', StudentAuthAPIView.as_view(), name='students'),
    path('<int:grade>/<int:room>/<int:number>', StudentDetailAPIView.as_view(), name='student_detail'),
    path('<int:grade>/<int:room>/<int:number>/login', StudentLoginAPIView.as_view(), name='student_login'),
    path('<int:grade>/<int:room>/<int:number>/check', StudentCheckAPIView.as_view(), name='student_check'),
    path('<int:grade>/<int:room>/<int:number>/password/change', StudentPasswordChangeAPIView.as_view(), name='student_password_change'),
    path('attendance/<int:grade>/<int:room>/<int:number>', AttendanceCheckAPIView.as_view(), name='attendance_check'),
    re_path(r'attendance/(?P<start_date>\d{4}-\d{2}-\d{2})/(?P<end_date>\d{4}-\d{2}-\d{2})/', StudentAttendanceAPIView.as_view(), name='student_attendnace_list'),
]

# urlpatterns += router.urls
