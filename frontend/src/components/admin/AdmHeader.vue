<script setup lang="ts">
import VIconButton from '@/components/common/VIconButton.vue';
import { logout } from '@/apis/services/auth';

defineEmits<{
    (e: 'email'): void;
    (e: 'password'): void;
}>();
</script>

<template>
    <div class="admin-header">
        <VIconButton
            class="admin-header__icon"
            text="홈"
            @click="$router.push({ name: 'admin-main' })">
            <font-awesome-icon icon="house" />
        </VIconButton>

        <VIconButton
            class="admin-header__icon"
            text="이메일 변경"
            @click="$emit('email')">
            <font-awesome-icon icon="house" />
        </VIconButton>

        <VIconButton
            class="admin-header__icon"
            text="password"
            @click="$emit('password')">
            <font-awesome-icon icon="house" />
        </VIconButton>

        <VIconButton class="admin-header__icon" text="로그아웃" @click="logout">
            <font-awesome-icon icon="house" />
        </VIconButton>
    </div>
</template>

<style lang="scss">
.admin-header {
    display: flex;
}
</style>
