from rest_framework.permissions import BasePermission


# Example: https://www.django-rest-framework.org/tutorial/4-authentication-and-permissions/#object-level-permissions
class CreateOnly(BasePermission):
    def has_permission(self, request, view):
        return request.method.upper() == 'POST'


class IsAdmin(BasePermission):
     def has_permission(self, request, view):
        user = request.user
        if user.role == 'admin' or user.is_staff:
            return True
        return False


class IsOwner(BasePermission):
    def has_object_permission(self, request, view, obj):
        # Write permissions are only allowed to the owner of the snippet.
        return obj.owner == request.user