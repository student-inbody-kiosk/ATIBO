import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', () => {
    const refreshToken = ref('');
    const accessToken = ref('');
    function logout() {
        refreshToken.value = '';
        accessToken.value = '';
    }
    return { refreshToken, accessToken, logout };
});
