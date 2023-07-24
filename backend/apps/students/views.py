import re
import jwt
from datetime import datetime, timedelta

from django.conf import settings
from django.db import transaction
from django.db.models import Q
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils import timezone
from django.utils.translation import gettext_lazy as _

from drf_spectacular.utils import extend_schema, OpenApiParameter, inline_serializer
from rest_framework import status, serializers
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.generics import GenericAPIView, RetrieveAPIView
from rest_framework.mixins import CreateModelMixin,ListModelMixin, RetrieveModelMixin, UpdateModelMixin, DestroyModelMixin
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken, AccessToken
from rest_framework_simplejwt.authentication import JWTAuthentication

from atibo.exceptions import DetailException
from atibo.authentications import StudentJWTAuthentication
from atibo.permissions import IsUserOrTheStudent
from .models import Student
from .serializers import StudentAuthSerializer, StudentCheckSerializer, StudentLoginSerializer, StudentDetailSerializer
from .regexes import student_name_regex


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
            return serializer_class(*args, **kwargs)    # Since ListModelMixin.list() add `many=True` internally 
        else:
            return serializer_class(many=True, *args, **kwargs)

    # Create dynamic query according to parameters
    def get_queryset(self):
        query_filter = Q()
        original_queryset = self.queryset

        grade = self.request.query_params.get('grade')
        room = self.request.query_params.get('room')
        number = self.request.query_params.get('number')
        name = self.request.query_params.get('name')

        query_filter = Q()
        if grade:
            if not grade.isdecimal():
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The grade must be a numeric value from 1 to 9'), 'invalid_grade')
            query_filter &= Q(grade=grade)
        if room:
            if not room.isdecimal():
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The room must be a numeric value from 1 to 99'), 'invalid_room')
            query_filter &= Q(room=room)
        if number:
            if not number.isdecimal():
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The number must be a numeric value from 1 to 99'), 'invalid_number')
            query_filter &= Q(number=number)
        if name:
            if not re.compile(student_name_regex).match(name):
                raise DetailException(status.HTTP_400_BAD_REQUEST, _('The name must be written in 2-5 Korean characters'), 'invalid_name')
            query_filter &= Q(name=name)

        queryset = original_queryset.filter(query_filter)

        return queryset
    
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
    
    @transaction.atomic
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)

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
    
    # Multiple Update
    def update(self, request, *args, **kwargs):
        # Get mutilple instance
        instance = []
        for student_data in request.data:
            try:
                instance.append(get_object_or_404(Student, id=student_data['id']))
            except Http404 as e:
                grade = student_data['grade']
                room = student_data['room']
                number = student_data['number']
                name = student_data['name']
                raise DetailException(status.HTTP_404_NOT_FOUND, _(f'Not found the student info of {grade}-{room}-{number} {name}'), 'sutdent_not_found')

        """
        UpdateModelMixin.update
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

        return Response(serializer.data)
    
    # Multiple Delete 
    def destroy(self, request, *args, **kwargs):
        student_ids = request.data.get('ids')

        # Ignore inavlid id
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

        student = Student.objects.filter(grade=grade, room=room, number=number)

        if len(student) == 1:
            student = student[0]
        elif len(student) == 0:
            raise DetailException(status.HTTP_404_NOT_FOUND, _(f'There\'s no corresponding student'), 'sutdent_not_found')
        else:
            raise DetailException(status.HTTP_409_CONFLICT, _(f'There\'re more than one student corresponding to this information. Please contact your administrator'), 'sutdent_too_many')

        return student


class StudentLoginAPIView(APIView):
    authentication_classes = []
    serializer_class = StudentLoginSerializer

    def post(self, request):
        serializer = StudentLoginSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        # create custom JWT
        student = serializer.validated_data.get('student')
        payload = {
            'id': str(student.id),
            'name': student.name,
            'grade': student.grade,
            'room': student.room,
            'number': student.number
        }
        exp = timezone.now() + timedelta(seconds=10)     # Set expiration in the data entity
        payload['exp'] = exp

        access_token = jwt.encode(payload, settings.SECRET_KEY, algorithm='HS256')

        # Return the Repsponse
        serializer = StudentLoginSerializer(data = {
            'access_token': access_token,
        })
        
        return Response(serializer.initial_data, status=status.HTTP_202_ACCEPTED)


class StudentDetailAPIView(RetrieveAPIView):
    authentication_classes = [StudentJWTAuthentication, JWTAuthentication]
    permission_classes = [IsUserOrTheStudent]
    serializer_class = StudentDetailSerializer

    def get_object(self):
        grade = self.kwargs['grade']
        room = self.kwargs['room']
        number = self.kwargs['number']

        student = Student.objects.filter(grade=grade, room=room, number=number)

        if len(student) == 1:
            student = student[0]
        elif len(student) == 0:
            raise DetailException(status.HTTP_404_NOT_FOUND, _(f'There\'s no corresponding student'), 'sutdent_not_found')
        else:
            raise DetailException(status.HTTP_409_CONFLICT, _(f'There\'re more than one student corresponding to this information. Please contact your administrator'), 'sutdent_too_many')

        return student
    pass


class StudentPasswordChangeAPIView(APIView):
    pass