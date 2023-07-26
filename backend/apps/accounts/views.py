from django.contrib.auth import get_user_model, authenticate
from django.core.mail import EmailMessage
from django.utils.translation import gettext_lazy as _

from drf_spectacular.utils import extend_schema, OpenApiParameter, inline_serializer
from rest_framework import status, serializers
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.generics import GenericAPIView, UpdateAPIView
from rest_framework.mixins import CreateModelMixin,ListModelMixin, RetrieveModelMixin, UpdateModelMixin, DestroyModelMixin
from rest_framework.viewsets import GenericViewSet
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken, AccessToken

from atibo.permissions import CreateOnly, IsAdminUser
from atibo.utils.password import generate_random_password
from .serializers import UserSerializer, LoginSerializer, UsernameCheckSerializer, EmailChangeSerializer, PasswordChangeSerializer, PasswordResetSerializer, TokenRefreshSerializer, AdminSerializer


"""
post: sign up
get: userinfo
delete: withdraw
"""
class AccountAPIView(GenericAPIView, CreateModelMixin, RetrieveModelMixin, DestroyModelMixin):
    permission_classes = [CreateOnly | IsAuthenticated]
    serializer_class = UserSerializer

    def get_object(self):
        return self.request.user
    
    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'post':
            return []  # Empty list for authentication on POST method
        else:
            return [auth() for auth in self.authentication_classes]
    
    def get(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)

    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)
    
    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)
    
    def destroy(self, request, *args, **kwargs):
        super().destroy(request, *args, **kwargs)
        return Response({'message': _('The user is successfully deleted')}, status=status.HTTP_204_NO_CONTENT)


@extend_schema(
    responses=inline_serializer(
        name="LoginResponseSerializer",
        fields={
            "accessToken": serializers.CharField(),
            "refreshToken": serializers.CharField(),
        },
    ),
)
class LoginAPIView(APIView):
    authentication_classes = []
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
        data = {
            'refresh_token': refresh_token,
            'access_token': access_token,
        }
        return Response(data, status=status.HTTP_202_ACCEPTED)


@extend_schema(
    responses=inline_serializer(
        name="LogoutResponseSerializer",
        fields={
            "message": serializers.CharField(),
        },
    ),
)
class LogoutAPIView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        # Delete Refresh Token
        user = request.user
        user.refresh_token = ''
        user.save()

        return Response({'message': _('Logged out successfully')}, status=status.HTTP_200_OK)


class UsernameCheckAPIView(APIView):
    authentication_classes = []
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
    http_method_names = ["put"]
    permission_classes = [IsAuthenticated]
    serializer_class = EmailChangeSerializer

    def get_object(self):
        return self.request.user


class PasswordChangeAPIView(UpdateAPIView):
    http_method_names = ["put"]
    permission_classes = [IsAuthenticated]
    serializer_class = PasswordChangeSerializer

    def get_object(self):
        return self.request.user
    
    def update(self, request, *args, **kwargs):
        super().update(request, *args, **kwargs)
        return Response({'message': _('The password is changed')}, status=status.HTTP_200_OK)
    

class PasswordResetAPIView(APIView):
    authentication_classes = []
    serializer_class = PasswordResetSerializer

    def put(self, request):
        serializer = PasswordResetSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        user = serializer.validated_data['user']
        email = serializer.validated_data['email']

        # Create and reset password
        new_password = generate_random_password()
        user.set_password(new_password)
        user.save()

        # Send Email
        email_message = EmailMessage(
                _('Your ATIBO password has been reset.'), # Subject
                _(f'New password : {new_password}'), # Body
                to=[email],
            )
        email_message.send()

        return Response({'message': _('A new password has been sent to your email')}, status=status.HTTP_200_OK)    


@extend_schema(
    responses=inline_serializer(
        name="TokenRefreshResponseSerializer",
        fields={
            "accessToken": serializers.CharField(),
        },
    ),
)
class TokenRefreshAPIView(APIView):
    authentication_classes = []
    serializer_class = TokenRefreshSerializer

    def post(self, request):
        serializer = TokenRefreshSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        user = serializer.validated_data.get('user')

        access = AccessToken.for_user(user) 
        access_token = str(access)

        return Response({'access_token': access_token}, status=status.HTTP_202_ACCEPTED)


"""
get: get normal user accounts
waiting: get normal user accounts waiting (boolean)
put: update an account to be active
delete: delete an account
"""
class AdminViewSet(GenericViewSet, ListModelMixin, UpdateModelMixin, DestroyModelMixin):
    http_method_names = ["get", "put", "delete"]
    permission_classes = [IsAuthenticated, IsAdminUser]
    serializer_class = AdminSerializer
    queryset = get_user_model().objects.filter(role='user')

    # Check there's an inactive user
    @extend_schema(
    responses=inline_serializer(
            name="AdminWatingSerializer",
            fields={
                "isWaiting": serializers.BooleanField(),
            },
        ),
    )
    @action(detail=False, methods=['get'])
    def waiting(self, request):
        if get_user_model().objects.filter(is_active=False).exists():
            return Response({'is_waiting': True}, status=status.HTTP_200_OK)
        else:
            return Response({'is_waiting': False}, status=status.HTTP_204_NO_CONTENT)
        
    
    def list(self, request, *args, **kwargs):
        """
        ListModelMixin.list()
        """
        queryset = self.filter_queryset(self.get_queryset())
        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)
        serializer = self.get_serializer(queryset, many=True)
        """
        """
        # Customize the serializer.data
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
    
    def destroy(self, request, *args, **kwargs):
        super().destroy(request, *args, **kwargs)
        return Response({'message': _('Deleted successfully')}, status=status.HTTP_204_NO_CONTENT)