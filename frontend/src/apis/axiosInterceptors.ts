import axios from 'axios';
import router from '@/router/index';
import { useAuthStore } from '@/stores/auth.store';

const axiosInstance = axios.create({
    baseURL: 'http://127.0.0.1:8000/api/',
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.request.use(
    function (config) {
        const { accessToken } = useAuthStore();

        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

axiosInstance.interceptors.response.use(
    function (response) {
        console.log(response);
        return response;
    },
    function (error) {
        console.log('ERROR >>>', error.response.data);
        const originalRequest = error.config;
        // Unauthorized : request a new accessToken
        if (
            error.response.status === 401 &&
            error.response.data?.code === 'token_not_valid'
        ) {
            const { refreshToken, updateAccessToken } = useAuthStore();
            axios
                .post('http://127.0.0.1:8000/api/accounts/token/refresh', {
                    username: 'admin',
                    refreshToken,
                })
                .then((res) => {
                    updateAccessToken(res.data?.accessToken);
                    originalRequest.headers.Authorization = `Bearer ${res.data.accessToken}`;
                    return axiosInstance(originalRequest);
                })
                .catch((error) => {
                    // request relogin
                    localStorage.removeItem('auth');
                    alert('다시 로그인해주세요.');
                    // redirect to index page based on the parent route
                    const currentRoute =
                        router.currentRoute.value?.matched[0]?.name;
                    currentRoute === 'admin'
                        ? router.push({ name: 'admin-index' })
                        : router.push({ name: 'kioks-index' });
                    return Promise.reject(error);
                });
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;
