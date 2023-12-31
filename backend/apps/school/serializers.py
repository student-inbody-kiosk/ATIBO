from django.conf import settings
from django.utils.translation import gettext_lazy as _

from rest_framework import serializers

from .models import School, DEFAULT_LOGO_IMAGE_PATH


class SchoolSerializer(serializers.ModelSerializer):
    class Meta:
        model = School 
        exclude = ['id']

    # Strip the name field
    def to_internal_value(self, data):
        ret = super().to_internal_value(data)
        ret['name'] = ret.get('name').strip()
        return ret
    
    # If the image is None, return the default Image
    def to_representation(self, instance):
        ret = super().to_representation(instance)

        # if null, return the default logo
        if not ret.get('logo_image'):
            # Development environment
            if not settings.DEBUG:
                ret['logo_image'] = settings.STATIC_URL + DEFAULT_LOGO_IMAGE_PATH
            # Deployment environment
            else:
                ret['logo_image'] = 'http://localhost:8000'+ settings.STATIC_URL + DEFAULT_LOGO_IMAGE_PATH
        
        elif not settings.DEBUG:
            ret['logo_image'] = settings.MEDIA_URL + str(instance.logo_image)
        return ret