<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import services from '@/apis/services';
import { getAccountInfo } from '@/apis/services/accounts';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import { useAccountStore } from '@/stores/accounts.store';
import { useAxios } from '@/hooks/useAxios';
import VLoading from '@/components/common/VLoading.vue';

const router = useRouter();
const username = ref('');
const password = ref('');
const { updateAccount } = useAccountStore();

const { fetchData: login, isLoading } = useAxios(null, services.login);

const handleLoginSubmit = function submitLogin() {
    login(username.value, password.value).then(() => {
        getAccountInfo().then((res) => {
            updateAccount(res);
            router.push({ name: 'admin-main' });
        });
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <form v-else class="login-form">
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
