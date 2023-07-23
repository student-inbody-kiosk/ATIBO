from django.contrib.auth import get_user_model, authenticate
from django.utils.translation import gettext_lazy as _

from rest_framework import serializers


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
    access = serializers.CharField(read_only=True)
    refresh = serializers.CharField(read_only=True)

    def validate(self, data):
        username = data.get('username')
        password = data.get('password')

        user = authenticate(request=self.context.get('request'), username=username, password=password)

        if not user:
            raise serializers.ValidationError(_('There\'s no corresponding user'))
        data['user'] = user

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
    confrim_password = serializers.CharField(write_only=True, required=True)

    def validate_old_password(self, value):
            user = self.context['request'].user
            if not user.check_password(value):
                raise serializers.ValidationError(_('Incorrect old password'))
            return value

    def validate(self, data):
        new_password = data.get('new_password')
        confirm_password = data.get('confirm_password')

        if new_password  != confirm_password:
            raise serializers.ValidationError(_('The new passwords do not match'))

        return data

    def update(self, instance, validated_data):
        # user = self.context['request'].user
        new_password = validated_data['new_password']
        instance.set_password(new_password)
        instance.save()
        return instance


class PasswordResetSerializer(serializers.Serializer):
    class Meta:
        model = get_user_model()
        fields = ['username', 'email']
        extra_kwargs = {
            'username': {'write_only': True},
            'eamil': {'write_only': True}
        }

    def validate(self, data):
        # 인증로직
        username = data.get('username')
        email = data.get('email')

        User = self.Meta.model
        user = User.objects.filter(username=username, email=email)

        if not user:
            raise serializers.ValidationError(_('There\'s no corresponding user'))
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