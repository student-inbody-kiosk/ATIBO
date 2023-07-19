from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .models import User

# https://docs.djangoproject.com/en/4.2/topics/auth/customizing/#using-a-custom-user-model-when-starting-a-project
admin.site.register(User, UserAdmin)
