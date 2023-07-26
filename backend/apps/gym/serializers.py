from django.conf import settings
from django.db.utils import IntegrityError
from django.utils.translation import gettext_lazy as _

from rest_framework import status, serializers

from atibo.exceptions import DetailException
from .models import Equipment, Image


class ImageListSerializer(serializers.ListSerializer):
    # Multiple Create/Update/Delete
    def update(self, instance, validated_data):
        image_mapping = {image.id: image for image in instance}
        data_mapping = {item['id']: item for item in validated_data}

        ret = []
        # Perform creations and updates.
        for inbody_id, data in data_mapping.items():
            image = image_mapping.get(inbody_id, None)
            if image is None:
                ret.append(self.child.create(data))
            else:
                ret.append(self.child.update(image, data))

        # Perform deletions.
        for image_id, image in image_mapping.items():
            if image_id not in data_mapping:
                image.delete()

        return ret

class ImageSerializer(serializers.ModelSerializer):
    id = serializers.IntegerField()    # For multiple update

    class Meta:
        model = Image
        exclude = ['equipment']
        
class EquipmentListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Equipment
        exclude = ['description']

class EquipmentSerializer(serializers.ModelSerializer):
    image_set = ImageSerializer(many=True, read_only=True)

    class Meta:
        model = Equipment
        fields = '__all__'
