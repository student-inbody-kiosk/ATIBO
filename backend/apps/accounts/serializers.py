from django.contrib.auth import get_user_model, authenticate
from django.contrib.auth.password_validation import validate_password
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from rest_framework import status, serializers
from rest_framework_simplejwt.tokens import RefreshToken

from atibo.exceptions import DetailException


class UserSerializer(serializers.ModelSerializer): # BaseAPI. get, create, delete
    class Meta:
        model = get_user_model()
        fields = ['id', 'username', 'name', 'email', 'role', 'comment', 'password']
        extra_kwargs = {
            'role': {'read_only': True},
            'password': {'write_only': True}
        }

    def to_internal_value(self, data):
        ret = super().to_internal_value(data)
        ret['comment'] = ret.get('comment').strip()
        return ret

    def create(self, validated_data):
        User = self.Meta.model
        validated_data['role'] = 'user'
        validated_data['is_active'] = False
        user = User.objects.create_user(**validated_data)
        return user


class LoginSerializer(serializers.Serializer):
    username = serializers.CharField(write_only=True)
    password = serializers.CharField(write_only=True)
    access_token = serializers.CharField(read_only=True)  # Not Necessary. Just for drf-spectacular.
    refresh_token = serializers.CharField(read_only=True) # Not Necessary. Just for drf-spectacular.

    def validate(self, data):
        username = data.get('username')
        password = data.get('password')

        try:
            user = get_object_or_404(get_user_model(), username=username)
        except Http404:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('Check username'), 'user_not_found')

        if not user.is_active:
            raise DetailException(status.HTTP_403_FORBIDDEN, _('The user is not active. Please contact your administrator'), 'inactive_user')
        
        authenticated_user = authenticate(request=self.context.get('request'), username=username, password=password)
        if not authenticated_user:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('Check username or password'), 'user_not_found')
        
        data['user'] = authenticated_user

        return data


class UsernameCheckSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ['username']


class EmailChangeSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ['email']


class PasswordChangeSerializer(serializers.Serializer):
    old_password = serializers.CharField(write_only=True, required=True)
    new_password = serializers.CharField(write_only=True, required=True)
    confirm_password = serializers.CharField(write_only=True, required=True)

    def validate_old_password(self, value):
            user = self.context['request'].user
            if not user.check_password(value):
                raise serializers.ValidationError(_('Incorrect old password'), 'invalid_old_password')
            return value
    
    def validate_new_password(self, value):
            # Note that validators will not be run automatically when you save a model
            # https://docs.djangoproject.com/en/4.2/ref/validators/#how-validators-are-run
            validate_password(value)
            return value

    def validate(self, data):
        new_password = data.get('new_password')
        confirm_password = data.get('confirm_password')

        if new_password != confirm_password:
            raise serializers.ValidationError({"confirm_password": _('The new passwords do not match')}, 'invalid_confirm_password')

        return data

    def update(self, instance, validated_data):
        # user = self.context['request'].user
        new_password = validated_data['new_password']
        instance.set_password(new_password)
        instance.save()
        return instance


class PasswordResetSerializer(serializers.Serializer):
    username = serializers.CharField(write_only=True, required=True)
    email = serializers.CharField(write_only=True, required=True)

    def validate(self, data):
        username = data.get('username')
        email = data.get('email')

        User = get_user_model()
        try:
            user = get_object_or_404(User, username=username, email=email)
        except Http404:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('Check username or email'), 'user_not_found')
        
        data['user'] = user

        return data


class TokenRefreshSerializer(serializers.Serializer):
    username = serializers.CharField(write_only=True)
    refresh_token = serializers.CharField(write_only=True)
    access_token = serializers.CharField(read_only=True)  # Not Necessary. Just for drf-spectacular.

    def validate(self, data):
        username = data.get('username')
        refresh_token = data.get('refresh_token')

        User = get_user_model()
        try:
            user = get_object_or_404(User, username=username)
        except Http404:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('Check username'), 'user_not_found')

        if not user.refresh_token == refresh_token:
            raise DetailException(status.HTTP_401_UNAUTHORIZED, _('You may be logged in from another place with that ID'), 'different_refresh_token')
        
        try:
            RefreshToken(refresh_token).verify()
        except:
            raise DetailException(status.HTTP_401_UNAUTHORIZED, _('Your session in terminated'), 'invalid_refresh_token')

        data['user'] = user

        return data


class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ['id', 'username', 'name', 'email', 'comment', 'is_active']
        read_only_fields = ['id', 'username', 'name', 'email', 'comment', 'is_active']

    def update(self, instance, validated_data):
        instance.is_active = True
        instance.save()
        return instance