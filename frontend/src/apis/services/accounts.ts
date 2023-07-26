import apiRequest from '@/apis/axiosInterceptors';

export function getAccountInfo() {
    return apiRequest
        .get('/accounts/')
        .then((res) => console.log('ACCOUNT INFORMATION', res.data));
}
