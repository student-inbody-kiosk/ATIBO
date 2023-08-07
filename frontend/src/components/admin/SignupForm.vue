<script setup lang="ts">
import VInput from '../common/VInput.vue';
import VButton from '@/components/common/VButton.vue';

import { ref } from 'vue';
import { useAuthInput, usePasswordCheck } from '@/utils/useAuthInput';
import services from '@/apis/services';
import type { AccountSignup } from '@/types/accounts.interface';

const name = ref('');
const comment = ref('');

const {
    result: usernameResult,
    value: username,
    handleInput: handleUsernameInput,
    condition: usernameCondition,
} = useAuthInput('username');

const {
    result: emailResult,
    value: email,
    handleInput: handleEmailInput,
    condition: emailCondition,
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
} = usePasswordCheck(password);

const handleSignupClick = function signup() {
    const accountData = {
        username: username.value,
        name: name.value,
        email: email.value,
        comment: comment.value,
        password: password.value,
    };
    services.signup(accountData).then(() => {
        emit('signup');
    });
};

const emit = defineEmits<{
    (e: 'signup'): void;
}>();
</script>

<template>
    <div>
        <VInput
            id="username"
            label="닉네임"
            :value="username"
            :placeholder="usernameCondition"
            @input="handleUsernameInput" />
        <VInput
            id="name"
            label="이름"
            :value="name"
            @input="(value) => (name = value)" />
        <VInput
            id="email"
            label="이메일"
            :value="email"
            :placeholder="emailCondition"
            @input="handleEmailInput" />
        <VInput
            id="password"
            type="password"
            label="비밀번호"
            :value="password"
            :placeholder="passwordCondition"
            @input="handlePasswordInput" />
        <VInput
            id="checkPassword"
            type="password"
            label="비밀번호 확인"
            :value="checkPassword"
            @input="handleCheckPassword" />
        <div v-if="password" class="result-message">
            {{
                checkPasswordResult
                    ? '비밀번호가 일치합니다.'
                    : ' 비밀번호가 일치하지 않습니다.'
            }}
        </div>
        <VInput
            id="comment"
            label="소개 문구"
            :value="comment"
            @input="(value) => (comment = value)" />
        <div v-if="comment.length < 10" class="result-message">
            소개 문구는 10자 이상으로 작성해주세요.
        </div>
        <VButton
            text="가입하기"
            color="admin-primary"
            @click="handleSignupClick" />
    </div>
</template>

<style lang="scss" scoped>
.result-message {
    color: $red;
    font-weight: 600;
}
</style>
