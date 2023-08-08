<script setup lang="ts">
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import { accountRegexes } from '@/constants/regexes';
import { useAxios } from '@/hooks/useAxios';
import { toastTopErrorMessage } from '@/utils/toastManager';
import type { AccountPwReset } from '@/types/accounts.interface';

const emit = defineEmits<{
    (e: 'success'): void;
}>();

const { fetchData: resetPw, isLoading } = useAxios<null, AccountPwReset>(
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
    <form class="pw-reset-form" @submit.prevent="handleSubmit">
        <VLoading v-if="isLoading" color="admin-primary" />
        <div v-else>
            <VInput
                id="pw-reset-username"
                name="username"
                type="text"
                label="아이디" />
            <VInput
                id="pw-reset-email"
                name="email"
                type="email"
                label="이메일" />
            <VButton text="비밀번호 초기화" color="green" type="submit" />
        </div>
    </form>
</template>

<style lang="scss"></style>
