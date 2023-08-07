import apiRequest from '@/apis/axiosInterceptors';
import router from '@/router/index';
import { useAuthStore } from '@/stores/auth.store';
import type { AccountSignup } from '@/types/accounts.interface';

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

export async function signup(account: AccountSignup) {
    return await apiRequest.post('/accounts/', account);
}
