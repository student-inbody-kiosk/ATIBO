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


# IsUser(BasePermission):
class IsUser(BasePermission):
     def has_permission(self, request, view):
        user = request.user
        if user.is_authenticated:
            return True
        return False


# Allow authenticated user or "the student"
# The student is specified by {'grade', 'room', 'number'} which are in URL or Query Params
class IsTheStudent(BasePermission):
     def has_permission(self, request, view):
        if request.auth != 'student':   # User
            return False
               
        # Check the permision of the student
        student = request.user
        print('student', student.grade, student.room, student.number)

        # Path variable
        grade = int(view.kwargs.get('grade'))
        room = int(view.kwargs.get('room'))
        number = int(view.kwargs.get('number'))
        if grade == student.grade and room == student.room and number == student.number:
            return True
        
        # Query Params
        grade = request.query_params.get('grade')
        room = request.query_params.get('room')
        number = request.query_params.get('number')
        if grade == student.grade and room == student.room and number == student.number:
            return True
        
        return False


class IsOwner(BasePermission):
    def has_object_permission(self, request, view, obj):
        # Write permissions are only allowed to the owner of the snippet.
        return obj.student == request.user