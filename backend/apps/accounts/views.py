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
from .serializers import UserSerializer, LoginSerializer, UsernameCheckSerializer, EmailChangeSerializer, PasswordChangeSerializer, PasswordResetSerializer, AdminSerializer
from .utils import generate_password

"""
회원가입
회원탈퇴
회원정보 조회
"""
class AccountAPIView(GenericAPIView, CreateModelMixin, RetrieveModelMixin, DestroyModelMixin):
    serializer_class = UserSerializer
    permission_classes = [IsAuthenticated | CreateOnly]

    def get_object(self):
        return self.request.user
    
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)
    
    def get(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)
    
    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)


class LoginAPIView(APIView):
    serializer_class = LoginSerializer

    def post(self, request):
        serializer = LoginSerializer(data=request.data)

        serializer.is_valid(raise_exception=True)

        user = serializer.validated_data.get('user')

        # Create jwt token manually
        refresh = RefreshToken.for_user(user)
        refresh_token = str(refresh)                
        access_token = str(refresh.access_token)   

        # Save the refresh token in the DB
        user.refresh_token = refresh_token 
        user.save()

        # Return the Repsponse
        serializer = LoginSerializer(data = {
            'refresh': refresh_token,
            'access': access_token,
        })
        
        return Response(serializer.initial_data, status=status.HTTP_202_ACCEPTED)


class LogoutAPIView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        # Delete Refresh Token
        user = request.user
        user.refresh_token = ''
        user.save()

        return Response({'message': _('Logged out successfully.')}, status=status.HTTP_200_OK)


class UsernameCheckAPIView(APIView):
    serializer_class = UsernameCheckSerializer

    def post(self, request):
        serializer = UsernameCheckSerializer(data=request.data)

        serializer.is_valid(raise_exception=True)

        User = get_user_model()
        username = serializer.validated_data.get('username')

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

        serializer.is_valid(raise_exception=True)

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


class AdminViewSet(GenericViewSet, ListModelMixin, UpdateModelMixin, DestroyModelMixin):
    queryset = get_user_model().objects.filter(role='user')
    serializer_class = AdminSerializer
    permission_classes = [IsAuthenticated, IsAdmin]
    http_method_names = ["get", "put", "delete"]

    @action(detail=False, methods=['get'])
    def waiting(self, request):
        if get_user_model().objects.filter(is_active=False).exists():
            return Response({'is_waiting': True}, status=status.HTTP_200_OK)
        else:
            return Response({'is_waiting': False}, status=status.HTTP_204_NO_CONTENT)
        
    
    def list(self, request, *args, **kwargs):
        """
        ListModelMixin.list
        """
        queryset = self.filter_queryset(self.get_queryset())

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)

        # Customize the final response representation
        users = serializer.data

        users_classified = {
            'inactive_users': [],
            'active_users': []
        }

        for user in users:
            if user.get('is_active'):
                users_classified['active_users'].append(user)
            else:
                users_classified['inactive_users'].append(user)

        return Response(users_classified, status=status.HTTP_200_OK)