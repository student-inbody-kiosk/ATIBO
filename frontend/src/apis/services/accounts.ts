import apiRequest from '@/apis/axiosInterceptors';

export function getAccountInfo() {
    return apiRequest.get('/accounts/');
}

export function deleteAccount(userId: number) {
    return apiRequest.delete(`/accounts/admin/${userId}/`);
}

export function acceptAccount(userId: number) {
    return apiRequest.put(`/accounts/admin/${userId}/`);
}
