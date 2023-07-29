<script setup lang="ts">
import TheInput from '@/components/common/TheInput.vue';
import TheButton from '@/components/common/TheButton.vue';

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
    <div>
        <TheInput
            type="text"
            refer="id"
            :value="username"
            label="아이디"
            @update-input="(value) => (username = value)"
            @submit="handleLoginSubmit" />
        <TheInput
            type="password"
            refer="password"
            :value="password"
            label="비밀번호"
            @update-input="(value) => (password = value)"
            @submit="handleLoginSubmit" />
        <TheButton
            text="로그인"
            color="admin-primary"
            size="md"
            emitMessage="login"
            @login="handleLoginSubmit" />
    </div>
</template>

<style lang="scss" scoped></style>
