from django.urls import path, re_path

from rest_framework import routers

from atibo.regexes import date_regex
from .views import StudentAuthAPIView, StudentCheckAPIView, StudentDetailAPIView, StudentLoginAPIView, StudentPasswordChangeAPIView, AttendanceCheckAPIView, StudentAttendanceAPIView, InbodyStudentAPIView, InbodyDetailAPIView, StudentInbodyAPIView, InbodyListAPIView

app_name = 'students'

from rest_framework import routers

# router = routers.SimpleRouter()
# router.register(r'inbody', InbodyViewSet)

urlpatterns = [
    path('', StudentAuthAPIView.as_view(), name='students'),
    path('<int:grade>/<int:room>/<int:number>/', StudentDetailAPIView.as_view(), name='student_detail'),
    path('<int:grade>/<int:room>/<int:number>/login/', StudentLoginAPIView.as_view(), name='student_login'),
    path('<int:grade>/<int:room>/<int:number>/check/', StudentCheckAPIView.as_view(), name='student_check'),
    path('<int:grade>/<int:room>/<int:number>/password/change/', StudentPasswordChangeAPIView.as_view(), name='student_password_change'),
    path('attendance/<int:grade>/<int:room>/<int:number>/', AttendanceCheckAPIView.as_view(), name='attendance_check'),
    re_path(r'attendance/(?P<start_date>{\d{4}-\d{2}-\d{2})}/(?P<end_date>\d{4}-\d{2}-\d{2})/', StudentAttendanceAPIView.as_view(), name='student_attendnace_list'),
    path('inbody/<int:grade>/<int:room>/<int:number>/', InbodyStudentAPIView.as_view(), name='inbody_create'), 
    path('inbody/<int:pk>/', InbodyDetailAPIView.as_view(), name='inbody_detail'),
    re_path(r'inbody/(?P<start_date>\d{4}-\d{2}-\d{2})/(?P<end_date>\d{4}-\d{2}-\d{2})/', StudentInbodyAPIView.as_view(), name='student_attendnace_list'),
    path('inbody/list/', InbodyListAPIView.as_view(), name='student_attendnace_list'),
]

# urlpatterns += router.urls
