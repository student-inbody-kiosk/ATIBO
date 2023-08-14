import { useAccountsStore } from '@/stores/accounts.store';
import axios from 'axios';
import router from '@/router/index';
import { useAuthStore } from '@/stores/auth.store';

// 개발시 환경변수에 IP와 PORT 정보가 있다면 해당 변수 사용
// 배포시 없다면 현재 URL 정보를 가져옴
const CURRENT_IP = import.meta.env.VITE_API_IP
    ? import.meta.env.VITE_API_IP
    : window.location.hostname;

const CURRENT_PORT = import.meta.env.VITE_API_PORT
    ? import.meta.env.VITE_API_PORT
    : window.location.port;

const API_BASE_URL = `http://${CURRENT_IP}:${CURRENT_PORT}/api`;

const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
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
                        `${API_BASE_URL}/accounts/token/refresh/`,
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
