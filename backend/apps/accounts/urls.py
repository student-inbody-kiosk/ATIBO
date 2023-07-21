from django.urls import path

from rest_framework import routers
from rest_framework_simplejwt.views import TokenRefreshView

from .views import AccountAPIView, LoginAPIView, LogoutAPIView, UsernameCheckAPIView, EmailChangeAPIView, PasswordChangeAPIView, PasswordResetAPIView, AdminViewSet

app_name = 'accounts'

router = routers.SimpleRouter()
router.register(r'admin', AdminViewSet)

urlpatterns = [
    path('', AccountAPIView.as_view(), name='user_detail'),
    path('login', LoginAPIView.as_view(), name='login'),
    path('logout', LogoutAPIView.as_view(), name='logout'),
    path('username/check', UsernameCheckAPIView.as_view(), name='username_check'),
    path('email/change', EmailChangeAPIView.as_view(), name='email_change'),
    path('password/change', PasswordChangeAPIView.as_view(), name='password_change'),
    path('password/reset', PasswordResetAPIView.as_view(), name='password_reset'),
    path('token/refresh', TokenRefreshView.as_view(), name='token_refresh'),
]

urlpatterns += router.urls
