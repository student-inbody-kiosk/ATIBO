<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';

import { useRouter } from 'vue-router';
import { ref } from 'vue';

import type { Account } from '@/types/admin.interace';
import type { AxiosResponse } from 'axios';

import { login } from '@/apis/services/auth';
import { getAccountInfo } from '@/apis/services/accounts';
import { useAccountsStore } from '@/stores/accounts.store';

const router = useRouter();
const username = ref('');
const password = ref('');
const { updateAccounts } = useAccountsStore();

const handleLoginSubmit = function submitLogin() {
    login(username.value, password.value).then(() => {
        getAccountInfo().then((res: AxiosResponse<Account>) => {
            updateAccounts(res?.data);
            router.push({ name: 'admin-main' });
        });
    });
};
</script>

<template>
    <form class="login-form">
        <VInput
            id="login-username"
            name="username"
            :value="username"
            label="아이디"
            size="md"
            @input="(value) => (username = value)"
            @enter="handleLoginSubmit" />
        <VInput
            id="login-password"
            name="password"
            type="password"
            :value="password"
            label="비밀번호"
            size="md"
            @input="(value) => (password = value)"
            @enter="handleLoginSubmit" />
        <VButton
            text="로그인"
            color="admin-primary"
            size="md"
            @click="handleLoginSubmit" />
    </form>
</template>

<style lang="scss" scoped>
.login-form {
    align-self: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    gap: 1rem;
    padding: 3rem 3rem 2rem 3rem;
    border-radius: 1rem;
    background-color: $admin-tertiary;
}
</style>
