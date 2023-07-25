import re
import pytz
from datetime import date, datetime, timedelta
from django.utils import timezone

from django.conf import settings
from django.db import transaction
from django.db.models import Q, Prefetch
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from drf_spectacular.utils import extend_schema, OpenApiParameter, inline_serializer
from rest_framework import status, serializers
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.generics import GenericAPIView, CreateAPIView, ListAPIView, RetrieveAPIView, UpdateAPIView, ListCreateAPIView, RetrieveUpdateDestroyAPIView
from rest_framework.mixins import CreateModelMixin,ListModelMixin, RetrieveModelMixin, UpdateModelMixin, DestroyModelMixin
from rest_framework.viewsets import GenericViewSet, ModelViewSet
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken, AccessToken
from rest_framework_simplejwt.authentication import JWTAuthentication

from atibo.authentications import StudentJWTAuthentication
from atibo.exceptions import DetailException
from atibo.permissions import IsUser, IsTheStudent, IsOwner
from atibo.regexes import korean_name_regex, date_regex
from atibo.utils.custom_token import encode
from .models import Student, Attendance, Inbody
from .serializers import StudentAuthSerializer, StudentCheckSerializer, StudentDetailSerializer, StudentPasswordChangeSerializer, AttendanceSerializer, StudentAttendanceSerializer, InbodySerializer, StudentInbodySerializer
from .utils import get_student_object_from_path_variables, get_student_queryset_from_query_params, get_date_from_path_variables, get_date_from_query_params

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
        return get_student_queryset_from_query_params(request.query_params)
    
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
        try:
            for student_data in request.data:
                instance.append(get_object_or_404(Student, id=student_data.get('id')))
        except Http404:
            grade = student_data.get('grade')
            room = student_data.get('room')
            number = student_data.get('number')
            name = student_data.get('name')
            raise DetailException(status.HTTP_404_NOT_FOUND, _(f'Not found the student info of {grade}-{room}-{number} {name}'), 'student_not_found')

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
        student = get_student_object_from_path_variables(self.kwargs)
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
        student = get_student_object_from_path_variables(self.kwargs)

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
    permission_classes = [IsUser | IsTheStudent]
    serializer_class = StudentDetailSerializer

    def get_object(self):
        student = get_student_object_from_path_variables(self.kwargs)

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
    permission_classes = [IsUser | IsTheStudent]
    serializer_class = StudentPasswordChangeSerializer

    def get_object(self):
        student = get_student_object_from_path_variables(self.kwargs)
        return student
    
    def update(self, request, *args, **kwargs):
        super().update(request, *args, **kwargs)
        return Response({ 'message': _('The password is changed')}, status=status.HTTP_200_OK)
    

class AttendanceCheckAPIView(CreateAPIView):
    authentication_classes = []
    serializer_class = AttendanceSerializer

    def perform_create(self, serializer):
        student = get_student_object_from_path_variables(self.kwargs)

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
        student_queryset = get_student_queryset_from_query_params(self.request.query_params)
        start_date, end_date = get_date_from_query_params(self.kwargs)
        return student_queryset.prefetch_related(Prefetch('attendance_set', queryset=Attendance.objects.filter(date_attended__gte=start_date, date_attended__lte=end_date)))


class InbodyStudentAPIView(ListCreateAPIView):
    authentication_classes = [StudentJWTAuthentication, JWTAuthentication]
    permission_classes = [IsUser | IsTheStudent]
    serializer_class = InbodySerializer

    # Create dynamic query according to parameters
    def get_queryset(self):
        student = get_student_object_from_path_variables(self.kwargs)
        start_date, end_date = get_student_queryset_from_query_params(self.request.query_parmas)
        return Inbody.objects.filter(student=student, test_date__gte=start_date, test_date__lte=end_date)
    
    @extend_schema(
        parameters=[
            OpenApiParameter(name='start_date', description=_('start date'), type=date),
            OpenApiParameter(name='end_date', description=_('end date'), type=date),
        ]
    )
    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def perform_create(self, serializer):
        student = get_student_object_from_path_variables(self.kwargs)

        serializer.save(student = student)  # It's converted as validated_data in the serialzer.save()
        
    
class InbodyDetailAPIView(RetrieveUpdateDestroyAPIView):
    http_method_names = ['get', 'put', 'delete',]
    authentication_classes = [StudentJWTAuthentication, JWTAuthentication]
    permission_classes = [IsUser | IsOwner]
    serializer_class = InbodySerializer
    queryset = Inbody.objects.all()
    
    def destroy(self, request, *args, **kwargs):
        super().destroy(request, *args, **kwargs)
        return Response({'message': _('The Inbody is successfully deleted')}, status=status.HTTP_204_NO_CONTENT)
    

@extend_schema(
        parameters=[
            OpenApiParameter(name='grade', description=_('student grade'), type=int),
            OpenApiParameter(name='room', description=_('student room'), type=int),
            OpenApiParameter(name='number', description=_('student number'), type=int),
            OpenApiParameter(name='name', description=_('student name'), type=str),
        ]
    )
class StudentInbodyAPIView(ListAPIView):
    permission_classes = [IsAuthenticated]
    serializer_class = StudentInbodySerializer

    # Create dynamic query according to parameters
    def get_queryset(self):
        student_queryset = get_student_queryset_from_query_params(self.request.query_params)
        start_date, end_date = get_date_from_path_variables(self.kwargs)
        return student_queryset.prefetch_related(Prefetch('inbody_set', queryset=Inbody.objects.filter(test_date__gte=start_date, test_date__lte=end_date)))


class InbodyListAPIView(GenericAPIView, UpdateModelMixin, DestroyModelMixin):
    permission_classes = [IsAuthenticated]
    serializer_class = InbodySerializer
    
    # Alsways list Serailzer
    def get_serializer(self, *args, **kwargs):
        serializer_class = self.get_serializer_class()
        kwargs.setdefault('context', self.get_serializer_context())
        return serializer_class(many=True, *args, **kwargs)
        
    # Multiple update
    @transaction.atomic
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)
    
    @extend_schema(
        request=inline_serializer(
            name="DeleteInbodySerializer",
            fields={
                "ids": serializers.ListField(child=serializers.IntegerField()),
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
        try:
            for inbody_data in request.data:
                id = inbody_data.get('id')
                if not id:
                    pass
                instance.append(get_object_or_404(Inbody, id=id))
        except Http404:
            test_date = inbody_data.get('test_date')
            raise DetailException(status.HTTP_404_NOT_FOUND, _(f'Not found the inbody info of {test_date}'), 'inbody_not_found')

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
        inbody_ids = request.data.get('ids')

        # Just ignore invalid id
        for id in inbody_ids:
            try:
                instance = Inbody.objects.get(id=id)
                self.perform_destroy(instance)
            except:
                pass

        return Response({'message': _('Deleted successfully')}, status=status.HTTP_204_NO_CONTENT)