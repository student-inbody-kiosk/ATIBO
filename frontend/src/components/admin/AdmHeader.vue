<script setup lang="ts">
import VIconButton from '@/components/common/VIconButton.vue';
import { logout } from '@/apis/services/auth';
</script>

<template lang="">
    <div class="admin-header">
        <VIconButton
            class="admin-header__icon"
            text="홈"
            @click="$router.push({ name: 'admin-index' })">
            <font-awesome-icon icon="house" />
        </VIconButton>

        <VIconButton
            class="admin-header__icon"
            text="이메일 변경"
            @click="$emit('open-modal', 'email')">
            <font-awesome-icon icon="house" />
        </VIconButton>

        <VIconButton
            class="admin-header__icon"
            text="password"
            @click="$emit('open-modal', 'password')">
            <font-awesome-icon icon="house" />
        </VIconButton>

        <VIconButton class="admin-header__icon" text="로그아웃" @cilck="logout">
            <font-awesome-icon icon="house" />
        </VIconButton>
    </div>
</template>

<style lang="scss">
.admin-header {
    display: flex;
}
</style>
