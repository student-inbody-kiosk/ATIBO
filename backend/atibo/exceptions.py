from django.utils.translation import gettext_lazy as _

from rest_framework import status
from rest_framework.response import Response
from rest_framework import status
from rest_framework.exceptions import APIException
from rest_framework.views import exception_handler


"""
Exception Handler
"""

# Change all the unknown error to '500 Internal Server Error'
def default_exception_handler(exc, context):
    # Call REST framework's default exception handler first,
    # to get the standard error response.
    response = exception_handler(exc, context)

    # if response is None:
    #     print('Error: ', exc, context)
    #     return Response({'message': _('예상치 못한 서버 에러가 발생했습니다. 개발자에게 문의해주세요')}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

    return response


"""
Exception
"""
class DetailException(APIException):
    """
    {
        "detail" : "string"
    }
    """
    status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    default_detail = _('서비스가 불안정 합니다. 잠시 후에 다시 시도해주세요')
    default_code = 'service_unavailable'

    def __init__(self, status=None, detail=None, code=None):
        if status:
            self.status_code = status
        if detail is None:
            detail = self.default_detail
        if code is None:
            code = self.default_code
        super().__init__(detail, code)