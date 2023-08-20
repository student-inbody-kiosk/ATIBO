import { ref } from 'vue';
import { defineStore } from 'pinia';
import services from '@/apis/services';
import type { Account } from '@/types/accounts.interace';

export const useAccountStore = defineStore('account', {
    state: () => {
        const account = ref<Account>({});
        return { account };
    },
    actions: {
        // update account synchronously
        updateAccount(payload: Account) {
            this.account = payload;
        },
        // update account asynchronously
        async getAccount() {
            try {
                this.account = await services.getAccountInfo();
            } catch (err) {
                return err;
            }
        },
    },
    persist: true,
});
