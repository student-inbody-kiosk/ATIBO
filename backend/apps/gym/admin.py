from django.contrib import admin
from .models import Equipment

@admin.register(Equipment)
class SchoolAdmin(admin.ModelAdmin):
    pass