from django.contrib import admin
from .models import Student, Attendance, Inbody

@admin.register(Student)
class StudentAdmin(admin.ModelAdmin):
    pass

@admin.register(Attendance)
class AttendanceAdmin(admin.ModelAdmin):
    pass

@admin.register(Inbody)
class InbodyAdmin(admin.ModelAdmin):
    pass