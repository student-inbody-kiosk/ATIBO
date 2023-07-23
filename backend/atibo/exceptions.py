from rest_framework import status
from rest_framework.exceptions import APIException


"""
{
  "detail" : "Hi"
}
"""
class DetailException(APIException):
    status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    default_detail = 'Service temporarily unavailable, try again later.'
    default_code = 'service_unavailable'

    def __init__(self, status=None, detail=None, code=None):
        if status:
            self.status_code = status
        if detail is None:
            detail = self.default_detail
        if code is None:
            code = self.default_code
        super().__init__(detail, code)