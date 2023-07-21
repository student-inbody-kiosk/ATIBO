from django.contrib import admin
from .models import Student

@admin.register(Student)
class StudenAdmin(admin.ModelAdmin):
    pass