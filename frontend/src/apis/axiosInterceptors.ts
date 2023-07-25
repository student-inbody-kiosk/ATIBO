import axios from 'axios';
import { useAuthStore } from '@/stores/auth.store';

const axiosInstance = axios.create({
    baseURL: 'http://127.0.0.1:8000/',
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.request.use(
    function (config) {
        const { refreshToken, accessToken } = useAuthStore();

        if (accessToken) {
            config.headers.Authorization = accessToken;
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
        console.log('ERROR >>>', error.response.data?.detail);
        return Promise.reject(error);
    }
);

export default axiosInstance;
