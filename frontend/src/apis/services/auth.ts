import apiRequest from '@/apis/axiosInterceptors';
import router from '@/router/index';
import { useAuthStore } from '@/stores/auth.store';
import type { AccountPwReset, AccountSignup } from '@/types/accounts.interface';
import {
    toastTopErrorMessage,
    toastTopSuccessMessage,
} from '@/utils/toastManager';

export async function login(username: string, password: string) {
    return await apiRequest
        .post('/accounts/login/', {
            username,
            password,
        })
        .then((res) => {
            const { updateAccessToken, updateRefreshToken } = useAuthStore();
            updateAccessToken(res.data.accessToken);
            updateRefreshToken(res.data.refreshToken);
        });
}

export async function logout() {
    return await apiRequest.post('/accounts/logout/').then(() => {
        const { updateAccessToken, updateRefreshToken } = useAuthStore();
        updateAccessToken('');
        updateRefreshToken('');
        router.push({ name: 'admin-index' });
    });
}

export async function signup(data: AccountSignup) {
    return await apiRequest
        .post('/accounts/', data)
        .then((res) => {
            toastTopSuccessMessage(
                '성공적으로 회원가입 되었습니다. 관리자의 승인 후 사용가능합니다'
            );
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('회원가입에 실패했습니다', err);
            throw err;
        });
}

export async function resetPw(data: AccountPwReset) {
    return await apiRequest
        .put('/accounts/password/reset/', data)
        .then((res) => {
            toastTopSuccessMessage('이메일로 새 비밀번호가 발송되었습니다');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('비밀번호 변경에 실패했습니다', err);
            throw err;
        });
}
