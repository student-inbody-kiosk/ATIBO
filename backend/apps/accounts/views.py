from django.contrib.auth import get_user_model, authenticate
from django.core.mail import EmailMessage
from django.utils.translation import gettext_lazy as _

from rest_framework import status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.generics import GenericAPIView, UpdateAPIView
from rest_framework.mixins import CreateModelMixin,ListModelMixin, RetrieveModelMixin, UpdateModelMixin, DestroyModelMixin
from rest_framework.viewsets import GenericViewSet
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.views import APIView

from atibo.permissions import CreateOnly, IsAdmin
from .serializers import UserSerializer, LoginSerializer, EmailChangeSerializer, PasswordChangeSerializer, PasswordResetSerializer, AdminSerializer
from .utils import generate_password

"""
회원가입
회원탈퇴
회원정보 조회
"""
class AccountAPIView(GenericAPIView, CreateModelMixin, RetrieveModelMixin, DestroyModelMixin):
    serializer_class = UserSerializer
    permission_classes = [IsAuthenticated, CreateOnly]

    def get_object(self):
        return self.request.user
    
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)
    
    def get(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)
    
    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)


class LoginAPIView(APIView):
    def post(self, request):
        serializer = LoginSerializer(data=request.data)

        if serializer.is_valid():
            user = serializer.validated_data.get('user')

            # Create jwt token manually
            refresh = RefreshToken.for_user(user)
            refresh_token = str(refresh)                
            access_token = str(refresh.access_token)   

            # Save the refresh token in the DB
            token = user.token
            token.refresh_token = refresh_token 
            token.save()

            # Return the Repsponse
            data = {
                'access_token': access_token,
                'refresh_token': refresh_token
            }
            return Response(data, status=status.HTTP_202_ACCEPTED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class LogoutAPIView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        # Delete Refresh Token
        user = request.user
        token = user.token
        token.refresh_token = ''
        token.save()

        return Response({'message': _('Logged out successfully.')}, status=status.HTTP_200_OK)


class UsernameCheckAPIView(APIView):
    def get(self, request):
        User = get_user_model()
        username = request.query_params.get('useranme')

        if User.objects.filter(username=username).exists():
            return Response({"duplicate": True}, status=status.HTTP_200_OK)
        else:
            return Response({"duplicate": False}, status=status.HTTP_200_OK)


class EmailChangeAPIView(UpdateAPIView):
    serializer_class = EmailChangeSerializer
    permission_classes = [IsAuthenticated]
    http_method_names = ["put"]

    def get_object(self):
        return self.request.user


class PasswordChangeAPIView(UpdateAPIView):
    serializer_class = PasswordChangeSerializer
    permission_classes = [IsAuthenticated]
    http_method_names = ["put"]

    def get_object(self):
        return self.request.user
    

class PasswordResetAPIView(APIView):
    def put(self, request):
        serializer = PasswordResetSerializer(data=request.data)

        if serializer.is_valid():
            # Find the user
            user = serializer.validated_data['user']
            email = serializer.validated_data['email']
            # Create and reset password
            new_password = generate_password()
            user.set_password(new_password)
            # Send Email
            email_message = EmailMessage(
                    _('Your ATIBO password has been reset.'), # Subject
                    _(f'New password : {new_password}'), # Body
                    to=[email], #받는 이메일
                )
            email_message.send()
            return Response({'message': _('A new password has been sent to your email')}, status=status.HTTP_200_OK)    
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# Make Vieset for the account
class AdminViewSet(GenericViewSet, ListModelMixin, UpdateModelMixin, DestroyModelMixin):
    """
    A viewset that provides the standard actions
    """
    queryset = get_user_model().objects.all()
    serializer_class = AdminSerializer
    permission_classes = [IsAdmin]

    @action(detail=True, methods=['get'])
    def waiting(self, request):
        if get_user_model().objects.filter(is_active=False).exists():
            return Response({'isWaiting': True}, status=status.HTTP_200_OK)
        else:
            return Response({'isWaiting': False},  status=status.HTTP_204_NO_CONTENT)