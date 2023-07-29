import { ref } from 'vue';
import { defineStore } from 'pinia';
import type { Account } from '@/types/admin.interace';

export const useAccountsStore = defineStore(
    'accounts',
    () => {
        const id = ref('');
        const username = ref('');
        const name = ref('');
        const email = ref('');
        const role = ref('');
        const updateAccounts = (data: Account): void => {
            id.value = data.id;
            username.value = data.username;
            name.value = data.name;
            email.value = data.email;
            role.value = data.role;
        };

        return { id, username, name, email, role, updateAccounts };
    },
    {
        persist: true,
    }
);
