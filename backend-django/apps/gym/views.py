from django.db import transaction
from django.utils.translation import gettext_lazy as _

from rest_framework import status
from rest_framework.response import Response
from rest_framework.viewsets import ModelViewSet
from rest_framework.generics import GenericAPIView
from rest_framework.mixins import ListModelMixin

from atibo.permissions import ReadOnly, IsAdminUser
from .models import Equipment, Image
from .serializers import EquipmentSerializer, EquipmentListSerializer, ImageSerializer


"""
list: get gym equipment list
get: get gym equipment detail
put: update gym equipment
post: create gym equipment
delete: delte gym equipment
"""
class EquipmentViewSet(ModelViewSet):
    http_method_names = ["get", "put", "post", "delete"]
    permission_classes = [ReadOnly | IsAdminUser]
    serializer_class = EquipmentSerializer
    queryset = Equipment.objects.all()

    # Different serializer list & retrieve
    def get_serializer_class(self):
        if self.action == 'list':
            return EquipmentListSerializer
        else:
            return super().get_serializer_class()

    # Allow read
    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'get':
            return []
        else:
            return [auth() for auth in self.authentication_classes] # JWTAuthentication
    
    def destroy(self, request, *args, **kwargs):
        super().destroy(request, *args, **kwargs)
        return Response({'message': _('운동기구가 삭제되었습니다')}, status=status.HTTP_204_NO_CONTENT)


"""
get: get gym equipment image_set
put: update gym equipment image_set (multiple update)
"""
class ImageAPIView(GenericAPIView, ListModelMixin):
    http_method_names = ["get", "put"]
    permission_classes = [ReadOnly | IsAdminUser]
    serializer_class = ImageSerializer
    queryset = Image.objects.all()

    # Allow read
    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'get':
            return [] 
        else:
            return [auth() for auth in self.authentication_classes] # JWTAuthentication

    def filter_queryset(self, queryset):
        equipment_id = self.kwargs['equipment_id']
        queryset = queryset.filter(equipment_id=equipment_id)
        return queryset

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)
    
    # Multiple create/update/delete
    @transaction.atomic
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)
    
    # Multiple create/update/delete
    def update(self, request, *args, **kwargs):
        equipment_id = self.kwargs['equipment_id']
        instance = Image.objects.filter(equipment_id=equipment_id)

        """
        Form data => Python dictionary
        Django parsor doesn't support nested formData

        [Input]
        <QueryDict: {'0[id]': '7', '0[image]': formData, '1[id]': ['9'], '1[image]': 'image_url'}>

        [Output]
        { 0: { id: 7, image: formData }, 1: { id: 9, } }
        """
        request_dict = {}
        for key, value in request.data.items():
            index, field = key[:-1].split('[')
            if not request_dict.get(index):
                request_dict[index] = {}
            if field == "image" and isinstance(value, str):   # Pop the image if it's url => to avoid imageSerializer validation error
                continue
            request_dict[index][field] = value

        """
        dictionary => list

        [Input]
        { 0: { id: 7, image: 'image_value_1' }, 1: { id: 9, image: 'image_value_2' } }

        [Output]
        [ { id: 7, image: 'image_value_1' }, { id: 9, image: 'image_value_2' } ]
        """
        request_data = []
        for data in request_dict.values():
            request_data.append(data)

        serializer = ImageSerializer(instance, data=request_data, many=True)
        serializer.is_valid(raise_exception=True)
        serializer.save(equipment_id=equipment_id)  # Multiple create/update/delete

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, 
            # we need to forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(status=status.HTTP_200_OK)
    