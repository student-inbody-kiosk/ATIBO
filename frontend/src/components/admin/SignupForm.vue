<script setup lang="ts">
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import { useAuthInput, usePasswordCheck } from '@/hooks/useAuthInput';
import { ref, computed } from 'vue';
import { toastTopErrorMessage } from '@/utils/toastManager';

const emit = defineEmits<{
    (e: 'success'): void;
}>();

const {
    value: username,
    result: usernameResult,
    condition: usernameCondition,
    handleInput: handleUsernameInput,
} = useAuthInput('username');

const {
    value: name,
    result: nameResult,
    condition: nameCondition,
    handleInput: handleNameInput,
} = useAuthInput('name');

const {
    value: email,
    result: emailResult,
    condition: emailCondition,
    handleInput: handleEmailInput,
} = useAuthInput('email');

const {
    result: passwordResult,
    value: password,
    handleInput: handlePasswordInput,
    condition: passwordCondition,
} = useAuthInput('password');

const {
    result: checkPasswordResult,
    value: checkPassword,
    handleInput: handleCheckPassword,
    condition: checkPasswordCondition,
} = usePasswordCheck(password);

const comment = ref('');
const handleCommentInput = function updateComment(value: string) {
    comment.value = value;
};
const commentResult = computed<boolean>(() => {
    const strippedComment = comment.value.replace(/ /g, '');
    return strippedComment.length > 9 ? true : false;
});

const handleSubmit = function signup(event: Event) {
    if (
        !usernameResult ||
        !nameResult ||
        !emailResult ||
        !passwordResult ||
        !checkPasswordResult ||
        !commentResult.value
    )
        toastTopErrorMessage('입력값을 다시 확인해주세요');

    const data = {
        username: username.value,
        name: name.value,
        email: email.value,
        comment: comment.value.trim(),
        password: password.value,
    };

    services.signup(data).then(() => {
        emit('success');
    });
};
</script>

<template>
    <form class="signup-form" @submit.prevent="handleSubmit">
        <VInput
            id="signup-form-username"
            label="아이디"
            name="username"
            :value="username"
            :isError="username ? !usernameResult : false"
            :condition="usernameCondition"
            size="md"
            @input="handleUsernameInput" />
        <VInput
            id="signup-form-name"
            label="이름"
            name="name"
            :value="name"
            :isError="name ? !nameResult : false"
            :condition="nameCondition"
            size="md"
            @input="handleNameInput" />
        <VInput
            id="signup-form-email"
            type="email"
            label="이메일"
            name="email"
            :value="email"
            :isError="email ? !emailResult : false"
            :condition="emailCondition"
            size="md"
            @input="handleEmailInput" />
        <VInput
            id="signup-form-password"
            type="password"
            label="비밀번호"
            name="password"
            :value="password"
            :isError="password ? !passwordResult : false"
            :condition="passwordCondition"
            size="md"
            @input="handlePasswordInput" />
        <VInput
            id="signup-form-checkPassword"
            type="password"
            label="비밀번호 확인"
            :value="checkPassword"
            :isError="checkPassword ? !checkPasswordResult : false"
            :condition="checkPasswordCondition"
            size="md"
            @input="handleCheckPassword" />
        <VInput
            id="signup-form-comment"
            label="소개 문구"
            name="comment"
            type="textarea"
            :value="comment"
            :isError="comment ? !commentResult : false"
            condition="소개 문구는 공백 제외 10자 이상 작성해주세요"
            size="md"
            @input="handleCommentInput" />
        <VButton
            class="signup-form__button"
            text="회원가입"
            type="submit"
            color="green" />
    </form>
</template>

<style lang="scss" scoped>
.signup-form {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 1rem;
    margin: 1rem 2rem;
}

.signup-form__button {
    align-self: center;
}
</style>
