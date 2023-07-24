from rest_framework.permissions import BasePermission


# Allow only POST method
class CreateOnly(BasePermission):
    def has_permission(self, request, view):
        return request.method.upper() == 'POST'


# Allow only admin user
class IsAdminUser(BasePermission):
     def has_permission(self, request, view):
        user = request.user
        if user.role == 'admin' or user.is_staff:
            return True
        return False


# Allow users or the student
# The student is specified by 'grade', 'room', 'number' in URL path
class IsUserOrTheStudent(BasePermission):
     def has_permission(self, request, view):

        if not request.auth:
            return False
        if request.auth != 'student':
            return True
        else:
            student = request.user

            print(student)

            grade = view.kwargs.get('grade')
            room = view.kwargs.get('room')
            number = view.kwargs.get('number')

            if grade == student.grade and room == student.room and number == student.number:
                print(1)
                return True

            return False


class IsOwner(BasePermission):
    def has_object_permission(self, request, view, obj):
        # Write permissions are only allowed to the owner of the snippet.
        return obj.owner == request.user