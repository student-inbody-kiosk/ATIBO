from django.urls import path

from rest_framework import routers

from .views import EquipmentViewSet, ImageAPIView


app_name = 'gym'

router = routers.SimpleRouter()
router.register(r'', EquipmentViewSet)

urlpatterns = [
    path('<int:equipment_id>/image/', ImageAPIView.as_view(), name='equipment_image'),
]

urlpatterns += router.urls