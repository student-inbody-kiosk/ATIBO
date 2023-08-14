from datetime import date

from django.db import transaction
from django.db.models import Prefetch
from django.http.response import Http404
from django.shortcuts import get_object_or_404
from django.utils.translation import gettext_lazy as _

from drf_spectacular.utils import extend_schema, OpenApiParameter, inline_serializer
from rest_framework import status, serializers
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.generics import GenericAPIView, CreateAPIView, ListAPIView, RetrieveAPIView, UpdateAPIView, ListCreateAPIView, RetrieveUpdateDestroyAPIView
from rest_framework.mixins import CreateModelMixin,ListModelMixin, UpdateModelMixin
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.authentication import JWTAuthentication

from atibo.authentications import StudentJWTAuthentication
from atibo.exceptions import DetailException
from atibo.permissions import  IsTheStudent, IsOwner
from atibo.utils.custom_token import encode
from atibo.utils.dummy_data import generateStudentDummyData
from .models import Student, Attendance, Inbody
from .serializers import StudentAuthSerializer, StudentCheckSerializer, StudentDetailSerializer, StudentPasswordChangeSerializer, AttendanceSerializer, StudentAttendanceSerializer, InbodySerializer, StudentInbodySerializer
from .utils import get_student_object_from_path_variables, get_student_queryset_from_query_params, get_date_from_path_variables, get_date_from_query_params, get_date_from_month_in_path_variables

class StudentAuthAPIView(GenericAPIView, ListModelMixin, CreateModelMixin, UpdateModelMixin):
    http_method_names = ["post", "get", "put", "patch"] # patch is for multiple deletion
    permission_classes = [IsAuthenticated]
    serializer_class = StudentAuthSerializer
    queryset = Student.objects.all()

    # Always list Serailzer
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
    
    # Multiple delete
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
    
    # Multiple update
    def update(self, request, *args, **kwargs):
        # Get mutilple instance
        instance = []
        for student_data in request.data:
            student = Student.objects.select_for_update().get(id=student_data.get('id'))    # lock the records for multiple update

            if not student:
                grade = student_data.get('grade')
                room = student_data.get('room')
                number = student_data.get('number')
                name = student_data.get('name')
                raise DetailException(status.HTTP_404_NOT_FOUND, _(f'제출한 {grade}학년 {room}반 {number}번호 {name} 학생의 기존 데이터가 없습니다'), 'student_not_found')

            """
            Temporarily Deactivate (grade, room, number) constraint for multiple update.
            It will be reactivated in the serializer
            """
            student.is_constraint_activated = False
            student.save()

            instance.append(student)

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
    
    # Multiple Delete : Ignore the invalid id
    def destroy(self, request, *args, **kwargs):
        student_ids = request.data.get('ids')
        Student.objects.filter(id__in=student_ids).delete()
        return Response({'message': _('학생 정보가 삭제되었습니다')}, status=status.HTTP_204_NO_CONTENT)


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
            raise DetailException(status.HTTP_400_BAD_REQUEST, _('비밀번호가 일치하지 않습니다'), 'invalid_student_password')

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
    permission_classes = [IsTheStudent | IsAuthenticated]
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
    permission_classes = [IsTheStudent | IsAuthenticated]
    serializer_class = StudentPasswordChangeSerializer

    def get_object(self):
        student = get_student_object_from_path_variables(self.kwargs)
        return student
    
    def update(self, request, *args, **kwargs):
        super().update(request, *args, **kwargs)
        return Response({ 'message': _('비밀번호가 변경되었습니다')}, status=status.HTTP_200_OK)
    

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
        start_date, end_date = get_date_from_month_in_path_variables(self.kwargs)
        return student_queryset.prefetch_related(Prefetch('attendance_set', queryset=Attendance.objects.filter(date_attended__gte=start_date, date_attended__lt=end_date).order_by('date_attended')))


# Inbody of a single student
class InbodyStudentAPIView(ListCreateAPIView):
    authentication_classes = [StudentJWTAuthentication, JWTAuthentication]
    permission_classes = [IsTheStudent | IsAuthenticated]
    serializer_class = InbodySerializer

    # Create dynamic query according to parameters
    def get_queryset(self):
        student = get_student_object_from_path_variables(self.kwargs)
        start_date, end_date = get_date_from_query_params(self.request.query_params)
        return Inbody.objects.filter(student=student, test_date__gte=start_date, test_date__lt=end_date)
    
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
    permission_classes = [IsOwner | IsAuthenticated]
    serializer_class = InbodySerializer
    queryset = Inbody.objects.all()
    
    def destroy(self, request, *args, **kwargs):
        super().destroy(request, *args, **kwargs)
        return Response({'message': _('인바디가 삭제되었습니다')}, status=status.HTTP_204_NO_CONTENT)
    

# Inbody of multiple students
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
        start_date, end_date = get_date_from_path_variables(self.kwargs, 730)   # max available period: 2years
        return student_queryset.prefetch_related(Prefetch('inbody_set', queryset=Inbody.objects.filter(test_date__gte=start_date, test_date__lt=end_date).order_by('test_date')))


# Update(Create), Delete Multiple Inbody
class InbodyListAPIView(GenericAPIView, UpdateModelMixin):
    permission_classes = [IsAuthenticated]
    serializer_class = InbodySerializer
    
    # Alsways list Serailzer
    def get_serializer(self, *args, **kwargs):
        serializer_class = self.get_serializer_class()
        kwargs.setdefault('context', self.get_serializer_context())
        return serializer_class(many=True, *args, **kwargs)
        
    # Multiple update/create
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

        for inbody_data in request.data:
            id = inbody_data.get('id')
            if not id:  # Data for creation
                pass

            inbody = Inbody.objects.select_for_update().get(id=id)    # lock the records for multiple update

            if not inbody:
                test_date = inbody_data.get('test_date')
                raise DetailException(status.HTTP_404_NOT_FOUND, _(f'{test_date} 날짜에 해당하는 기존 인바디 정보가 없습니다'), 'inbody_not_found')

            """
            Temporarily Deactivate (student, test_date) constraint for multiple update.
            It will be reactivated in the serializer
            """
            inbody.is_constraint_activated = False
            inbody.save()

            instance.append(inbody)

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
    
    # Multiple Delete : Ignore invalid id
    def destroy(self, request, *args, **kwargs):
        inbody_ids = request.data.get('ids')
        Inbody.objects.filter(id__in=inbody_ids).delete()
        return Response({'message': _('인바디가 삭제되었습니다')}, status=status.HTTP_204_NO_CONTENT)

"""
Create dummy data. Only for superuser
"""
class CreateDummyAPIView(APIView):

    @transaction.atomic
    def post(self, request, *args, **kwargs):
        if not request.user.is_superuser:
            return Response(status=status.HTTP_403_FORBIDDEN)
        print('Start Generating Dummy')
        generateStudentDummyData()
        return Response(status=status.HTTP_201_CREATED)