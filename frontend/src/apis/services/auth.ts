import apiRequest from '@/apis/axiosInterceptors';
import { useAuthStore } from '@/stores/auth.store';

export function login(username: string, password: string) {
    return apiRequest
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
