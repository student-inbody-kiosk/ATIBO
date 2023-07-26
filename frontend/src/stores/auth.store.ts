import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore(
    'auth',
    () => {
        const refreshToken = ref('');
        const accessToken = ref('');
        const updateAccessToken = (token: string) => {
            accessToken.value = token;
        };
        const updateRefreshToken = (token: string) => {
            refreshToken.value = token;
        };

        return {
            refreshToken,
            accessToken,
            updateAccessToken,
            updateRefreshToken,
        };
    },
    {
        persist: true,
    }
);
