import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore(
    'auth',
    () => {
        const refreshToken = ref('');
        const accessToken = ref('');

        return { refreshToken, accessToken };
    },
    {
        persist: true,
    }
);
