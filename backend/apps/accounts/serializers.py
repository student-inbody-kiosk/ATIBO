from django.contrib.auth import get_user_model, authenticate
from django.contrib.auth.password_validation import validate_password
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from rest_framework import status, serializers
from rest_framework_simplejwt.tokens import RefreshToken

from atibo.exceptions import DetailException


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ['id', 'username', 'name', 'email', 'role', 'comment', 'password']
        extra_kwargs = {
            'role': {'read_only': True},
            'password': {'write_only': True}
        }

    # strip the comment data
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
    username = serializers.CharField(write_only=True, required=True)
    password = serializers.CharField(write_only=True, required=True)

    def validate(self, data):
        username = data.get('username')
        password = data.get('password')

        # Find the user with username
        try:
            user = get_object_or_404(get_user_model(), username=username)
        except Http404:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('해당 아이디의 사용자 정보가 없습니다'), 'user_not_found')

        # Check whether the user is active
        if not user.is_active:
            raise DetailException(status.HTTP_403_FORBIDDEN, _('해당 계정은 활성화되지 않았습니다. 관리자에게 승인을 받으세요'), 'inactive_user')
        
        # Authenticate with username and apssword
        authenticated_user = authenticate(request=self.context.get('request'), username=username, password=password)
        if not authenticated_user:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('비밀번호가 틀렸습니다'), 'user_not_found')
        
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
            raise serializers.ValidationError(_('기존 비밀번호가 올바르지 않습니다'), 'invalid_old_password')
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
            raise serializers.ValidationError({"confirm_password": _('새 비밀번호가 서로 일치하지 않습니다')}, 'invalid_confirm_password')

        return data

    def update(self, instance, validated_data):
        new_password = validated_data['new_password']
        instance.set_password(new_password)
        instance.save()
        return instance


class PasswordResetSerializer(serializers.Serializer):
    username = serializers.CharField(write_only=True, required=True)
    email = serializers.CharField(write_only=True, required=True)

    def validate(self, data):
        User = get_user_model()
        username = data.get('username')
        email = data.get('email')

        # Find the user with username and email
        try:
            user = get_object_or_404(User, username=username, email=email)
        except Http404:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('아이디 혹은 이메일을 다시 확인해주세요'), 'user_not_found')
        
        data['user'] = user
        return data


class TokenRefreshSerializer(serializers.Serializer):
    username = serializers.CharField(write_only=True, required=True)
    refresh_token = serializers.CharField(write_only=True, required=True)

    def validate(self, data):
        User = get_user_model()
        username = data.get('username')
        refresh_token = data.get('refresh_token')

        # Find the user with username
        try:
            user = get_object_or_404(User, username=username)
        except Http404:
            raise DetailException(status.HTTP_404_NOT_FOUND, _('해당 아이디의 계정이 없습니다'), 'user_not_found')

        # Compare the refresh_token (Concurrent Session Controll)
        if not user.refresh_token == refresh_token:
            raise DetailException(status.HTTP_401_UNAUTHORIZED, _('해당 아이디로 중복 로그인이 발생했습니다. 비밀번호를 변경해주세요'), 'different_refresh_token')
        
        # Check the RefreshToken
        try:
            RefreshToken(refresh_token).verify()
        except:
            raise DetailException(status.HTTP_401_UNAUTHORIZED, _('세션이 만료되었습니다'), 'invalid_refresh_token')

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