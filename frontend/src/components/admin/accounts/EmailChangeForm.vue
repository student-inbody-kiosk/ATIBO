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

const { fetchData: changeEmail, isLoading } = useAxios(
    null,
    services.changeEmail
);

const handleSubmit = function changeAuthEmail(event: Event) {
    const form = event.target as HTMLFormElement;
    if (!form) return;

    const formData = new FormData(form);

    const email = formData.get('email') as string;
    if (!email || !accountRegexes.email.reg.test(email)) {
        toastTopErrorMessage(accountRegexes.email.condition);
        return;
    }
    const checkEmail = formData.get('check-email') as string;
    if (email !== checkEmail) {
        toastTopErrorMessage('이메일이 일치하지 않습니다');
        return;
    }

    changeEmail({ email }).then(() => {
        emit('success');
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <form v-else class="email-change-form" @submit.prevent="handleSubmit">
        <div class="form-title">이메일 변경</div>
        <div class="form-input">
            <VInput
                id="email"
                name="email"
                type="email"
                size="md"
                label="이메일" />
            <VInput
                id="check-email"
                name="check-email"
                type="email"
                size="md"
                label="이메일 확인" />
        </div>
        <VButton text="변경하기" color="green" type="submit" />
    </form>
</template>

<style lang="scss" scoped>
.form-title {
    font-size: 1.3rem;
    font-weight: 600;
}

.email-change-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    margin: 1.5rem 1rem 0.7rem 1rem;
}

.form-input {
    display: grid;
    gap: 1rem;
}
</style>
