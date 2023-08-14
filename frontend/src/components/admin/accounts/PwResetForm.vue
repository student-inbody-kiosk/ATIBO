<script setup lang="ts">
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import { accountRegexes } from '@/constants/regexes';
import { useAxios } from '@/hooks/useAxios';
import { toastTopErrorMessage } from '@/utils/toastManager';

const emit = defineEmits<{
    (e: 'success'): void;
}>();

const { fetchData: resetPw, isLoading } = useAxios<null>(
    null,
    services.resetPw
);

const handleSubmit = function resetPwRequest(event: Event) {
    const form = event.target as HTMLFormElement;
    if (!form) return;

    const formData = new FormData(form);

    const username = formData.get('username') as string;
    if (!username || !accountRegexes.username.reg.test(username)) {
        toastTopErrorMessage(accountRegexes.username.condition);
    }
    const email = formData.get('email') as string;
    if (!email || !accountRegexes.email.reg.test(email)) {
        toastTopErrorMessage(accountRegexes.username.condition);
    }

    resetPw({ username, email }).then((res) => {
        emit('success');
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <form v-else class="pw-reset-form" @submit.prevent="handleSubmit">
        <VInput
            id="pw-reset-username"
            name="username"
            type="text"
            size="md"
            label="아이디" />
        <VInput
            id="pw-reset-email"
            name="email"
            type="email"
            size="md"
            label="이메일" />
        <VButton text="비밀번호 초기화" color="green" type="submit" />
    </form>
</template>

<style lang="scss">
.pw-reset-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    margin: 1.5rem 1rem 0.7rem 1rem;
}
</style>
