<script setup lang="ts">
import AdmLayout from '@/components/admin/AdmLayout.vue';
import IconButton from '@/components/common/IconButton.vue';
import TheInput from '@/components/common/TheInput.vue';

import { useRouter } from 'vue-router';

import { ref } from 'vue';

const router = useRouter();
const userId = ref('');
const userPassword = ref('');

const handleIdInput = function updateUserId(value: string) {
    userId.value = value;
};

const handlePasswordInput = function updateUserPassword(value: string) {
    userPassword.value = value;
};

const handleLoginSubmit = function login() {
    console.log(userId.value, userPassword.value);
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
                    :value="userId"
                    label="아이디"
                    @update-input="handleIdInput"
                    @submit="handleLoginSubmit" />
                <TheInput
                    type="password"
                    refer="password"
                    :value="userPassword"
                    label="비밀번호"
                    @update-input="handlePasswordInput"
                    @submit="handleLoginSubmit" />
                <button @click="handleLoginSubmit">로그인</button>
            </div>
        </template>
    </AdmLayout>
</template>

<style lang="scss" scoped></style>
