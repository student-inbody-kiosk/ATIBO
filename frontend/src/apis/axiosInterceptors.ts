import { useAccountsStore } from '@/stores/accounts.store';
import axios from 'axios';
import router from '@/router/index';
import { useAuthStore } from '@/stores/auth.store';

const VITE_BASE_URL = import.meta.env.VITE_BASE_URL;

const axiosInstance = axios.create({
    baseURL: VITE_BASE_URL,
    // headers: {
    //     'Content-Type': 'application/json',
    // },
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
    async function (error) {
        console.log('ERROR >>>', error.response.data);
        const originalRequest = error.config;
        // Unauthorized : request a new accessToken
        const { refreshToken, updateAccessToken, updateRefreshToken } =
            useAuthStore();

        if (error.response.status === 401 || error.response.status === 403) {
            if (refreshToken) {
                try {
                    const { username } = useAccountsStore();
                    const res = await axios.post(
                        `${VITE_BASE_URL}accounts/token/refresh/`,
                        { username, refreshToken }
                    );
                    const accessToken = res.data?.accessToken;
                    updateAccessToken(accessToken);
                    originalRequest.headers.Authorization = `Bearer ${accessToken}`;
                    return axiosInstance(originalRequest);
                } catch (error) {
                    alert('다시 로그인해주세요.');
                    router.push({ name: 'admin-index' });
                    updateAccessToken(null);
                    updateRefreshToken(null);
                    return Promise.reject(error);
                }
            } else {
                console.log(2);
                router.push({ name: 'kiosk-index' });
            }
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;
