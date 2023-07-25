import apiRequest from '@/apis/axiosInterceptors';
import { useAuthStore } from '@/stores/auth.store';

export async function login(username: string, password: string) {
    return await apiRequest
        .post('/accounts/login', {
            username,
            password,
        })
        .then((res) => {
            const authStore = useAuthStore();
            const { refreshToken, accessToken } = res.data;
            authStore.refreshToken = refreshToken;
            authStore.accessToken = accessToken;
        });
}

export async function logout() {
    return await apiRequest.post('/accounts/logout').then(() => {
        localStorage.removeItem('auth');
    });
}
