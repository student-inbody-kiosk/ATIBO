<script setup lang="ts">
import { ref } from 'vue';
import { logout } from '@/apis/services/auth';
import VIconButton from '@/components/common/VIconButton.vue';

defineEmits<{
    (e: 'email'): void;
    (e: 'password'): void;
}>();

const isEmailModalOpen = ref(false);
const isPasswordModalOpen = ref(false);

const handleModalClose = function closeModal() {
    isEmailModalOpen.value = false;
    isPasswordModalOpen.value = false;
};
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

    <!-- modal -->
    <TheModal
        color="admin-secondary"
        v-if="isEmailModalOpen || isPasswordModalOpen"
        @close-modal="handleModalClose">
        <div v-if="isEmailModalOpen">Email Form</div>
        <div v-else>passwordform</div>
    </TheModal>
</template>

<style lang="scss">
.admin-header {
    display: flex;
}
</style>
