from django import forms
from django.contrib import admin

# from ckeditor_uploader.widgets import CKEditorUploadingWidget
from ckeditor.widgets import CKEditorWidget

from .models import Equipment, Image


class EquipmentAdminForm(forms.ModelForm):
    description = forms.CharField(widget=CKEditorWidget())
    class Meta:
        model = Equipment
        fields = '__all__'

@admin.register(Equipment)
class EquipmentAdmin(admin.ModelAdmin):
    form = EquipmentAdminForm

@admin.register(Image)
class ImagetAdmin(admin.ModelAdmin):
    pass