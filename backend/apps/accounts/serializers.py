from django.contrib.auth import get_user_model
from django.utils.translation import gettext_lazy as _

from rest_framework import serializers


class UserSerializer(serializers.ModelSerializer): # BaseAPI. get, create, delete
    class Meta:
        model = get_user_model()
        fields = ['id', 'username', 'name', 'email', 'password', 'comment']
        extra_kwargs = {
            'password': {'write_only': True}
        }

    def create(self, validated_data):
        User = self.Meta.model
        user = User.objects.create_user(**validated_data)   # user emthod
        return user


class LoginSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        fields = ['username', 'password']
        extra_kwargs = {
            'username': {'write_only': True},
            'password': {'write_only': True}
        }

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

    def update(self, user, validated_data):
        # user = self.context['request'].user
        new_password = validated_data['new_password']
        user.set_password(new_password)
        user.save()
        return user

