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

        return { refreshToken, accessToken, updateAccessToken };
    },
    {
        persist: true,
    }
);
