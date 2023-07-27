from django.db import transaction
from django.utils.translation import gettext_lazy as _
from rest_framework.response import Response
from rest_framework.viewsets import ModelViewSet
from rest_framework.views import APIView

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


class ImageAPIView(APIView):
    http_method_names = ["put",]
    permission_classes = [IsAdminUser]
    serializer_class = ImageSerializer
    
    @transaction.atomic
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)
    
    # Multiple Update
    def update(self, request, *args, **kwargs):
        equipment_id = self.kwargs['equipment_id ']
        instance = Image.objects.get(equipment=equipment_id)

        serializer = ImageSerializer(instance, data=request.data, many=True)
        serializer.is_valid(raise_exception=True)
        serializer.save()

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(serializer.data)
    