from django.contrib.auth import get_user_model, authenticate

from rest_framework import status
from rest_framework.response import Response
from rest_framework.mixins import CreateModelMixin, RetrieveModelMixin, DestroyModelMixin
from rest_framework.generics import GenericAPIView, UpdateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.views import APIView
from rest_framework_simplejwt.tokens import RefreshToken

from .serializers import UserSerializer, LoginSerializer, EmailChangeSerializer, PasswordChangeSerializer


"""
회원가입
회원탈퇴
회원정보 조회
"""
class AccountAPIView(GenericAPIView, CreateModelMixin, RetrieveModelMixin, DestroyModelMixin):
    # queryset = get_user_model().objects.all()
    serializer_class = UserSerializer

    def get_object(self):
        return self.request.user

    def get(self, request, *args, **kwargs):
        self.permission_classes = [IsAuthenticated]
        return self.retrieve(request, *args, **kwargs)

    def delete(self, request, *args, **kwargs):
        self.permission_classes = [IsAuthenticated]
        return self.destroy(request, *args, **kwargs)

    # def post(self, request, *args, **kwargs):
    #     return self.create(request, *args, **kwargs)


class LoginAPIView(APIView):
    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        if serializer.is_valid():
            username = serializer.validated_data['username']
            password = serializer.validated_data['password']
            user = authenticate(request, username=username, password=password)
            if not user:
                return Response({'message': 'There\'s no corresponding user'}, status=status.HTTP_400_BAD_REQUEST)
 
            # jwt 토큰 생성
            refresh = RefreshToken.for_user(user)
            refresh_token = str(refresh)                # refresh token 생성
            access_token = str(refresh.access_token)    # access token 생성

            user['refresh_token'] = refresh_token          # 사용자 DB에 refresh token 저장
            user.save()

            # 반환
            data = {
            'meessage': 'Successfully logined',
            'access': access_token,
            'refresh': refresh_token
            }
            return Response(data, status=status.HTTP_202_ACCEPTED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class LogoutAPIView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        # jwt 토큰 제거
        user=request.user
        user.get['refresh_token'] = ''
        user.save()

        return Response({'message': 'Logged out successfully.'}, status=status.HTTP_200_OK)


class UsernameCheckAPIView(APIView):
    def get(self, request):
        User = get_user_model()
        username = request.query_params.get('useranme')

        if User.objects.filter(username=username).exists():
            return Response({"duplicate": True}, status=status.HTTP_200_OK)
        else:
            return Response({"duplicate": False}, status=status.HTTP_200_OK)

class EmailChangeAPIView(UpdateAPIView):
    serializer_class = EmailChangeSerializer
    permission_classes = [IsAuthenticated]

    def get_object(self):
        return self.request.user


class PasswordChangeAPIView(UpdateAPIView):
    serializer_class = PasswordChangeSerializer
    permission_classes = [IsAuthenticated]

    def get_object(self):
        return self.request.user

# Make Vieset for the account
