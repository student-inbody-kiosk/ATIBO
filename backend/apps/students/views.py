import re
import pytz
from datetime import datetime, timedelta
from django.utils import timezone

from django.conf import settings
from django.db import transaction
from django.db.models import Q, Prefetch
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from drf_spectacular.utils import extend_schema, OpenApiParameter, inline_serializer
from rest_framework import status, serializers
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.generics import GenericAPIView, CreateAPIView, ListAPIView, RetrieveAPIView, UpdateAPIView
from rest_framework.mixins import CreateModelMixin,ListModelMixin, RetrieveModelMixin, UpdateModelMixin, DestroyModelMixin
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken, AccessToken
from rest_framework_simplejwt.authentication import JWTAuthentication
from atibo.authentications import StudentJWTAuthentication
from atibo.exceptions import DetailException
from atibo.permissions import IsUserOrTheStudent
from atibo.regexes import korean_name_regex
from atibo.utils.custom_token import encode
from .models import Student, Attendance
from .serializers import StudentAuthSerializer, StudentCheckSerializer, StudentDetailSerializer, StudentPasswordChangeSerializer, AttendanceSerializer, StudentAttendanceSerializer
from .utils import get_student_grade_room_number, get_student_queryset_from_query_params

class StudentAuthAPIView(GenericAPIView, ListModelMixin, CreateModelMixin, UpdateModelMixin, DestroyModelMixin):
    http_method_names = ["post", "get", "put", "patch", "delete"]
    permission_classes = [IsAuthenticated]
    serializer_class = StudentAuthSerializer
    queryset = Student.objects.all()

    # Alsways list Serailzer
    def get_serializer(self, *args, **kwargs):
        serializer_class = self.get_serializer_class()
        kwargs.setdefault('context', self.get_serializer_context())

        if self.request and self.request.method == 'GET':
            return serializer_class(*args, **kwargs)    # ListModelMixin.list() add `many=True` internally 
        else:
            return serializer_class(many=True, *args, **kwargs)

    # Create dynamic query according to parameters
    def get_queryset(self):
        request = self.request
        return get_student_queryset_from_query_params(request)
    
    @extend_schema(
        parameters=[
            OpenApiParameter(name='grade', description=_('student grade'), type=int),
            OpenApiParameter(name='room', description=_('student room'), type=int),
            OpenApiParameter(name='number', description=_('student number'), type=int),
            OpenApiParameter(name='name', description=_('student name'), type=str),
        ]
    )
    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)
    
    # Multiple create
    @transaction.atomic
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)

    # Multiple update
    @transaction.atomic
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)
    
    @extend_schema(
        request=inline_serializer(
            name="DeleteStudentSerializer",
            fields={
                "ids": serializers.ListField(child=serializers.UUIDField()),
            },
        ),
    )
    def patch(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)
    
    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)
    
    # Multiple update
    def update(self, request, *args, **kwargs):
        # Get mutilple instance
        instance = []
        for student_data in request.data:
            try:
                instance.append(get_object_or_404(Student, id=student_data['id']))
            except Http404:
                grade = student_data['grade']
                room = student_data['room']
                number = student_data['number']
                name = student_data['name']
                raise DetailException(status.HTTP_404_NOT_FOUND, _(f'Not found the student info of {grade}-{room}-{number} {name}'), 'sutdent_not_found')

        """
        UpdateModelMixin.update()
        """
        partial = kwargs.pop('partial', False)
        # instance = self.get_object()
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)
        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}
        """
        """
        return Response(serializer.data)
    
    # Multiple Delete 
    def destroy(self, request, *args, **kwargs):
        student_ids = request.data.get('ids')

        # Just ignore invalid id
        for id in student_ids:
            try:
                instance = Student.objects.get(id=id)
                self.perform_destroy(instance)
            except:
                pass

        return Response({'message': _('Deleted successfully')}, status=status.HTTP_204_NO_CONTENT)


class StudentCheckAPIView(RetrieveAPIView):
    authentication_classes = []
    serializer_class = StudentCheckSerializer

    def get_object(self):
        grade = self.kwargs['grade']
        room = self.kwargs['room']
        number = self.kwargs['number']

        student = get_student_grade_room_number(grade, room, number)
        return student


@extend_schema(
    request=inline_serializer(
        name="StudentLoginSerializer",
        fields={
            "password": serializers.CharField(),
        },
    ),
)
class StudentLoginAPIView(APIView):
    authentication_classes = []

    def post(self, request, *args, **kwargs):
        grade = self.kwargs['grade']
        room = self.kwargs['room']
        number = self.kwargs['number']

        student = get_student_grade_room_number(grade, room, number)

        password = request.data.get('password')
        if not student.password == password:
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('The password is not correct'), 'invalid_student_password')

        # Create Custom Token
        payload = {
            'id': str(student.id),
        }
        access_token = encode(payload, minutes=30)

        # Return the Repsponse
        data = {
            'refresh_token': None,
            'access_token': access_token,
        }
        return Response(data, status=status.HTTP_202_ACCEPTED)


class StudentDetailAPIView(RetrieveAPIView):
    authentication_classes = [StudentJWTAuthentication, JWTAuthentication]
    permission_classes = [IsUserOrTheStudent]
    serializer_class = StudentDetailSerializer

    def get_object(self):
        grade = self.kwargs['grade']
        room = self.kwargs['room']
        number = self.kwargs['number']

        student = get_student_grade_room_number(grade, room, number)

        return student

@extend_schema(
    responses=inline_serializer(
        name="StudentPasswordChangeResponseSerializer",
        fields={
            "message": serializers.CharField(),
        },
    ),
)
class StudentPasswordChangeAPIView(UpdateAPIView):
    http_method_names = ["put"]
    authentication_classes = [StudentJWTAuthentication, JWTAuthentication]
    permission_classes = [IsUserOrTheStudent]
    serializer_class = StudentPasswordChangeSerializer

    def get_object(self):
        grade = self.kwargs['grade']
        room = self.kwargs['room']
        number = self.kwargs['number']

        student = get_student_grade_room_number(grade, room, number)
        return student
    
    def update(self, request, *args, **kwargs):
        super().update(request, *args, **kwargs)
        return Response({ 'message': _('The password is changed')}, status=status.HTTP_200_OK)
    

class AttendanceCheckAPIView(CreateAPIView):
    authentication_classes = []
    serializer_class = AttendanceSerializer

    def perform_create(self, serializer):
        grade = self.kwargs['grade']
        room = self.kwargs['room']
        number = self.kwargs['number']
        student = get_student_grade_room_number(grade, room, number)

        serializer.save(student = student)  # It's converted as validated_data in the serialzer.save()


@extend_schema(
        parameters=[
            OpenApiParameter(name='grade', description=_('student grade'), type=int),
            OpenApiParameter(name='room', description=_('student room'), type=int),
            OpenApiParameter(name='number', description=_('student number'), type=int),
            OpenApiParameter(name='name', description=_('student name'), type=str),
        ]
    )
class StudentAttendanceAPIView(ListAPIView):
    permission_classes = [IsAuthenticated]
    serializer_class = StudentAttendanceSerializer

    # Create dynamic query according to parameters
    def get_queryset(self):
        request = self.request
        student_queryset = get_student_queryset_from_query_params(request)

        start_date = self.kwargs['start_date']
        end_date = self.kwargs['end_date']

        tz = pytz.timezone(settings.TIME_ZONE)
        start_date = datetime.strptime(start_date, "%Y-%m-%d")
        start_date = timezone.make_aware(start_date, timezone=tz)
        end_date = datetime.strptime(end_date, "%Y-%m-%d")
        end_date = timezone.make_aware(end_date, timezone=tz) + timedelta(days=1)

        return student_queryset.prefetch_related(Prefetch('attendance_set', queryset=Attendance.objects.filter(date_attended__gte=start_date, date_attended__lte=end_date)))
