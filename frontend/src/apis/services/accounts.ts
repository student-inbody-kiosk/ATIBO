import apiRequest from '@/apis/axiosInterceptors';
import {
    toastTopErrorMessage,
    toastTopSuccessMessage,
} from '@/utils/toastManager';

export function getAccountInfo() {
    return apiRequest.get('/accounts/').then((res) => {
        return res.data;
    }).catch((err) => {
        toastTopErrorMessage('계정 정보를 불러오지 못했습니다', err);
        throw err;
    });
}

export function getSchoolAccounts() {
    return apiRequest
        .get('accounts/admin/')
        .then((res): AccountList => {
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('계정 정보를 불러오지 못했습니다', err);
            throw err;
        });
}

export function deleteAccount(userId: number) {
    return apiRequest
        .delete(`/accounts/admin/${userId}/`)
        .then((res) => {
            toastTopSuccessMessage('계정을 삭제했습니다');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('계정 삭제에 실패했습니다', err);
            throw err;
        });
}

export function acceptAccount(userId: number) {
    return apiRequest
        .put(`/accounts/admin/${userId}/`)
        .then((res) => {
            toastTopSuccessMessage('계정을 승인했습니다');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('계정 승인에 실패했습니다', err);
            throw err;
        });
}
