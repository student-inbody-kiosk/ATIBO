<script setup lang="ts">
import AdmLayout from '@/components/admin/AdmLayout.vue';
import IconButton from '@/components/common/IconButton.vue';
import TheInput from '@/components/common/TheInput.vue';
import TheButton from '@/components/common/TheButton.vue';

import { useRouter } from 'vue-router';
import { ref } from 'vue';

import { login } from '@/apis/services/auth';

const router = useRouter();
const username = ref('');
const password = ref('');

const handleIdInput = function updateUserId(value: string) {
    username.value = value;
};

const handlePasswordInput = function updateUserPassword(value: string) {
    password.value = value;
};

const handleLoginSubmit = function submitLogin() {
    login(username.value, password.value);
    console.log(username.value, password.value);
    router.push({ name: 'admin-main' });
};
</script>

<template>
    <AdmLayout>
        <template #admin-header>
            <IconButton
                text="키오스크"
                emitMessage="routing"
                @routing="$router.push({ name: 'kiosk' })">
                <template #icon>
                    <font-awesome-icon icon="house" />
                </template>
            </IconButton>
        </template>

        <template #admin-main>
            <div>
                <TheInput
                    type="text"
                    refer="id"
                    :value="username"
                    label="아이디"
                    @update-input="handleIdInput"
                    @submit="handleLoginSubmit" />
                <TheInput
                    type="password"
                    refer="password"
                    :value="password"
                    label="비밀번호"
                    @update-input="handlePasswordInput"
                    @submit="handleLoginSubmit" />
                <TheButton
                    text="로그인"
                    color="admin-primary"
                    size="md"
                    emitMessage="login"
                    @login="handleLoginSubmit" />
            </div>
        </template>
    </AdmLayout>
</template>

<style lang="scss" scoped></style>
