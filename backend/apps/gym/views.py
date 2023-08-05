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


class EquipmentViewSet(ModelViewSet):
    http_method_names = ["get", "put", "post", "delete"]
    permission_classes = [ReadOnly | IsAdminUser]
    serializer_class = EquipmentSerializer
    queryset = Equipment.objects.all()

    def get_serializer_class(self):
        if self.action == 'list':
            return EquipmentListSerializer
        else:
            return super().get_serializer_class()

    def get_authenticators(self):
        if self.request and self.request.method.lower() == 'get':
            return []  # Empty list for authentication on get  method
        else:
            return [auth() for auth in self.authentication_classes]

    def get_queryset(self):
        queryset = super().get_queryset()
        return queryset.prefetch_related('image_set')
    
    def destroy(self, request, *args, **kwargs):
        super().destroy(request, *args, **kwargs)
        return Response({'message': _('Deleted successfully')}, status=status.HTTP_204_NO_CONTENT)


class ImageAPIView(GenericAPIView, ListModelMixin):
    http_method_names = ["put", "get"]
    permission_classes = [IsAdminUser]
    serializer_class = ImageSerializer
    queryset = Image.objects.all()

    def filter_queryset(self, queryset):
        equipment_id = self.kwargs['equipment_id']
        queryset.filter(equipment_id=equipment_id)
        return queryset

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)
    
    @transaction.atomic
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)
    
    # Multiple Update
    def update(self, request, *args, **kwargs):
        equipment_id = self.kwargs['equipment_id']
        instance = Image.objects.filter(equipment_id=equipment_id)

        # form data to python dictionary
        # Django parsor doesn't support nested formData
        # <QueryDict: {'0[id]': ['7'], '0[image]': ['image_value_1'], '1[id]': ['9'], '1[image]': ['image_value-2']}>
        request_dict = {}
        for key, value in request.data.items():
            index, field = key[:-1].split('[')
            if not request_dict.get(index):
                request_dict[index] = {}
            if field=="image" and isinstance(value, str):   # pop the string image value
                continue
            request_dict[index][field] = value

        # dictionary to list -> listSerializer
        request_data = []
        for data in request_dict.values():
            request_data.append(data)

        serializer = ImageSerializer(instance, data=request_data, many=True)
        serializer.is_valid(raise_exception=True)
        serializer.save(equipment_id=equipment_id)

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(status=status.HTTP_200_OK)
    