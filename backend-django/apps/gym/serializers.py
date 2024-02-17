from django.conf import settings
from django.utils.translation import gettext_lazy as _

from rest_framework import serializers

from .models import Equipment, Image


class ImageListSerializer(serializers.ListSerializer):
    # Multiple create/update/delete
    def update(self, instance, validated_data):
        # Instance mapping
        image_mapping = {image.id: image for image in instance}
        # Data mapping
        data_mapping = {}
        invalid_id = -1
        for item in validated_data:
            data_id = item.get('id')
            # Existing data => update
            if data_id:
                data_mapping[data_id] = item
            # Non Existing data => create
            else:
                data_mapping[invalid_id] = item
                invalid_id -= 1 # allocate invalid id temporarily

        # Perform creations and updates(pass).
        ret = []
        for image_id, data in data_mapping.items():
            image = image_mapping.get(image_id, None)
            # Create
            if image is None:
                ret.append(self.child.create(data))
            # Update (pass)
            else:
                ret.append(image) 

        # Perform deletions
        for image_id, image in image_mapping.items():
            if image_id not in data_mapping:
                image.delete()

        return ret


class ImageSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField(required=False)    # For multiple update
    image = serializers.ImageField(required=False)

    class Meta:
        list_serializer_class = ImageListSerializer
        model = Image
        exclude = ['equipment']

    def to_representation(self, instance):
        ret = super().to_representation(instance)
        if not settings.DEBUG:
            ret['image'] = settings.MEDIA_URL + str(instance.image)
        return ret
        

class EquipmentListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Equipment
        exclude = ['description']


class EquipmentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Equipment
        fields = '__all__'
