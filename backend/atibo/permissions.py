from rest_framework.permissions import BasePermission, SAFE_METHODS


# Example
# https://www.django-rest-framework.org/tutorial/4-authentication-and-permissions/#object-level-permissions


class IsOwner(BasePermission):

    def has_object_permission(self, request, view, obj):

        # Write permissions are only allowed to the owner of the snippet.
        return obj.owner == request.user