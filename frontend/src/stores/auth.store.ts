import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
    state: () => {
        const refreshToken = ref('');
        const accessToken = ref('');

        return {
            refreshToken,
            accessToken,
        };
    },
    actions: {
        updateAccessToken(token: string) {
            this.accessToken = token;
        },
        updateRefreshToken(token: string) {
            this.refreshToken = token;
        },
    },
    // default : localStorage
    // refreshToken will be stored with the key name 'ATIBO'
    persist: {
        key: 'ATIBO',
        paths: ['refreshToken'],
    },
});
