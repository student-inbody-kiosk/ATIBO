<script setup lang="ts">
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import { accountRegexes } from '@/constants/regexes';
import { useAxios } from '@/hooks/useAxios';
import { toastTopErrorMessage } from '@/utils/toastManager';
import { useAuthInput, usePasswordCheck } from '@/hooks/useAuthInput';

const emit = defineEmits<{
    (e: 'success'): void;
}>();

const { fetchData: changePassword, isLoading } = useAxios<null>(
    null,
    services.changePassword
);

const { value: oldPassword, handleInput: handleOldPasswordInput } =
    useAuthInput('password');

const {
    result: newPasswordResult,
    value: newPassword,
    handleInput: handleNewPasswordInput,
    condition: newPasswordCondition,
} = useAuthInput('password');

const {
    result: checkPasswordResult,
    value: checkPassword,
    handleInput: handleCheckPassword,
    condition: checkPasswordCondition,
} = usePasswordCheck(newPassword);

const handleSubmit = function changeAuthPassword() {
    if (!oldPassword.value || !newPassword.value || !checkPassword.value) {
        toastTopErrorMessage('입력값을 다시 확인해주세요');
        return;
    }

    if (!newPasswordResult) {
        toastTopErrorMessage(accountRegexes.password.condition);
        return;
    }

    if (newPassword.value !== checkPassword.value) {
        toastTopErrorMessage('새비밀번호가 일치하지 않습니다');
        return;
    }

    changePassword({
        oldPassword: oldPassword.value,
        newPassword: newPassword.value,
        confirmPassword: checkPassword.value,
    }).then(() => {
        emit('success');
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <form v-else class="pw-change-form" @submit.prevent="handleSubmit">
        <div class="form-title">비밀번호 변경</div>
        <div class="form-input">
            <VInput
                id="old-password"
                name="old-password"
                type="password"
                :value="oldPassword"
                size="md"
                label="기존 비밀번호"
                @input="handleOldPasswordInput" />
            <VInput
                id="new-password"
                name="new-password"
                type="password"
                :value="newPassword"
                :isError="newPassword ? !newPasswordResult : false"
                :condition="newPasswordCondition"
                size="md"
                label="새 비밀번호"
                @input="handleNewPasswordInput" />
            <VInput
                id="check-new-password"
                name="check-new-password"
                type="password"
                :value="checkPassword"
                :isError="checkPassword ? !checkPasswordResult : false"
                :condition="checkPasswordCondition"
                size="md"
                label="새 비밀번호 확인"
                @input="handleCheckPassword" />
        </div>
        <VButton text="비밀번호 변경" color="green" type="submit" />
    </form>
</template>

<style lang="scss" scoped>
.form-title {
    font-size: 1.3rem;
    font-weight: 600;
}
.pw-change-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    margin: 1.5rem 1rem 0.7rem 1rem;
}

.form-input {
    display: grid;
    gap: 1.5rem;
}
</style>
