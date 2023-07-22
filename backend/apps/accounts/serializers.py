from django.shortcuts import get_object_or_404
from django.contrib.auth import get_user_model, authenticate
from django.utils.translation import gettext_lazy as _

from rest_framework import serializers
from rest_framework_simplejwt.tokens import Token, RefreshToken, AccessToken

from django.contrib.auth.password_validation import validate_password

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
    access = serializers.CharField(read_only=True)  # Not Necessary. Just for drf-spectacular.
    refresh = serializers.CharField(read_only=True) # Not Necessary. Just for drf-spectacular.

    def validate(self, data):
        username = data.get('username')
        password = data.get('password')

        user = get_user_model().objects.get(username=username)
        if not user.is_active:
            raise serializers.ValidationError(_('The user is not active. Please contact your administrator'), 'inactive_user')
        
        authenticated_user = authenticate(request=self.context.get('request'), username=username, password=password)
        print(username, password, authenticated_user)
        if not authenticated_user:
            raise serializers.ValidationError(_('There\'s no corresponding user'))
        
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
                raise serializers.ValidationError(_('Incorrect old password'))
            return value
    
    def validate_new_password(self, value):
            validate_password(value)
            return value

    def validate(self, data):
        new_password = data.get('new_password')
        confirm_password = data.get('confirm_password')

        if new_password != confirm_password:
            raise serializers.ValidationError(_('The new passwords do not match'))

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
        user = get_object_or_404(User, username=username, email=email)

        if not user:
            raise serializers.ValidationError(_('There\'s no corresponding user'))
        data['user'] = user

        return data


class TokenRefreshSerializer(serializers.Serializer):
    username = serializers.CharField(write_only=True)
    refresh = serializers.CharField(write_only=True)
    access = serializers.CharField(read_only=True)  # Not Necessary. Just for drf-spectacular.

    def validate(self, data):
        username = data.get('username')
        refresh_token = data.get('refresh_token')

        User = get_user_model()
        user = get_object_or_404(User, username=username)

        if not user.refresh_token == refresh_token:
            raise serializers.ValidationError(_('You are logged in from another place with that ID'), 'different_refresh_token')
        
        try:
            RefreshToken(refresh_token).verify()
        except:
            raise serializers.ValidationError(_('Your session in terminated'), 'invalid_refresh_token')

        data['user'] = user

        return data


class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        # list_serializer_class = AdminListSerializer
        model = get_user_model()
        fields = ['id', 'username', 'name', 'email', 'comment', 'is_active']
        read_only_fields = ['id', 'username', 'name', 'email', 'comment', 'is_active']

    def update(self, instance, validated_data):
        instance.is_active = True
        instance.save()
        return instance