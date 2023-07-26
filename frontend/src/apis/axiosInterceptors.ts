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
        const { refreshToken, updateAccessToken } = useAuthStore();

        if (error.response.status === 401) {
            if (refreshToken.length) {
                return axios
                    .post('http://127.0.0.1:8000/api/accounts/token/refresh/', {
                        username: 'admin',
                        refreshToken,
                    })
                    .then((res) => {
                        console.log(res);
                        updateAccessToken(res.data?.accessToken);
                        originalRequest.headers.Authorization = `Bearer ${res.data.accessToken}`;
                        return axiosInstance(originalRequest);
                    })
                    .catch((error) => {
                        alert('다시 로그인해주세요.');
                        router.push({ name: 'admin-index' });
                        return Promise.reject(error);
                    });
            } else {
                router.push({ name: 'kiosk-index' });
            }
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;
