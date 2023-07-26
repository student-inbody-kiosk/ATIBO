import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useAccountsStore = defineStore('accounts', () => {
    const id = ref('');
    const username = ref('');
    const name = ref('');
    const email = ref('');
    const role = ref('');
    const updateAccounts = (data: {
        id: string;
        username: string;
        name: string;
        email: string;
        role: string;
    }) => {
        id.value = data.id;
        username.value = data.username;
        name.value = data.name;
        email.value = data.email;
        role.value = data.role;
    };

    return { id, username, name, email, role, updateAccounts };
});
