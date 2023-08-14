import { ref } from 'vue';
import { defineStore } from 'pinia';
import type { Account } from '@/types/accounts.interace';

export const useAccountStore = defineStore('account', {
    state: () => {
        const account = ref<Account>({});
        return { account };
    },
    actions: {
        updateAccount(payload: Account) {
            this.account = payload;
        },
    },
    persist: true,
});
