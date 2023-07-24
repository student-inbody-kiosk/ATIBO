import apiRequest from '@/apis/axiosInterceptors';
import { useAuthStore } from '@/stores/auth.store';

export function login(username: string, password: string) {
    return apiRequest
        .post('/api/accounts/login', {
            username,
            password,
        })
        .then((res) => {
            const { refreshToken, accessToken } = useAuthStore();
            console.log('토큰 가져와라', res.data);
        });
}
