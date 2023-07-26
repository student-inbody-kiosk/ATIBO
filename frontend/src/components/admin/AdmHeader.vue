<script setup lang="ts">
import IconButton from '@/components/common/IconButton.vue';
import { logout } from '@/apis/services/auth';
</script>

<template lang="">
    <div class="admin-header">
        <IconButton
            class="admin-header__icon"
            text="홈"
            emitMessage="routing"
            @routing="$router.push({ name: 'admin-index' })"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>

        <IconButton
            class="admin-header__icon"
            text="이메일 변경"
            emitMessage="modal"
            @modal="$emit('open-modal', 'email')"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>

        <IconButton
            class="admin-header__icon"
            text="password"
            emitMessage="modal"
            @modal="$emit('open-modal', 'password')"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>

        <IconButton
            class="admin-header__icon"
            text="로그아웃"
            emitMessage="logout"
            @logout="logout"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>
    </div>
</template>

<style lang="scss">
.admin-header {
    display: flex;
}
</style>
