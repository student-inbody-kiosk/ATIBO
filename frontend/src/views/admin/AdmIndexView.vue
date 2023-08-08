<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';

import { useRouter } from 'vue-router';
import { ref } from 'vue';

import services from '@/apis/services';
import { getAccountInfo } from '@/apis/services/accounts';
import { useAccountsStore } from '@/stores/accounts.store';
import { useAuthInput } from '@/utils/useAuthInput';
import { toastTopErrorMessage } from '@/utils/toastManager';

const router = useRouter();
const username = ref('');
const { updateAccounts } = useAccountsStore();

const {
    value: password,
    handleInput: handlePasswordInput,
    condition: passwordCondition,
} = useAuthInput('password');

const handleLoginSubmit = function login() {
    if (!username.value) {
        toastTopErrorMessage('아이디를 입력해주세요');
        return;
    } else if (!password.value) {
        toastTopErrorMessage('비밀번호를 입력해주세요');
        return;
    }

    services.login(username.value, password.value).then(() => {
        getAccountInfo().then((res) => {
            updateAccounts(res?.data);
            router.push({ name: 'admin-main' });
        });
    });
};
</script>

<template>
    <div class="login-container">
        <VInput
            id="id"
            :value="username"
            label="아이디"
            @input="(value) => (username = value)"
            @enter="handleLoginSubmit" />
        <VInput
            id="password"
            type="password"
            :value="password"
            label="비밀번호"
            :placeholder="passwordCondition"
            @input="handlePasswordInput"
            @enter="handleLoginSubmit" />
        <VButton
            text="로그인"
            color="admin-primary"
            size="sm"
            @click="handleLoginSubmit" />
    </div>
</template>

<style lang="scss" scoped>
.login-container {
    align-self: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    width: 50%;
    height: 40%;
    padding: 0.5rem 2rem 0 0;
    border-radius: 1rem;
    background-color: $admin-tertiary;
}

button {
    align-self: flex-end;
}
</style>
