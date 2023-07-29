import apiRequest from '@/apis/axiosInterceptors';

export function getAccountInfo() {
    return apiRequest.get('/accounts/');
}
