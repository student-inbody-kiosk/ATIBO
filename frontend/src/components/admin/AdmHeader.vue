<script setup lang="ts">
import IconButton from '@/components/common/IconButton.vue';

const handleModal = function openModal(message: string) {
    console.log(`open ${message} modal`);
};

const handlLogout = function logoutAndRedirect() {
    console.log('logout');
};
</script>

<template lang="">
    <div class="admin-header">
        <IconButton
            class="admin-header__icon"
            text="관리자홈"
            emitMessage="routing"
            @routing="$router.push({ name: admin })"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>

        <IconButton
            class="admin-header__icon"
            text="이메일 변경"
            emitMessage="modal"
            @modal="() => handleModal('email')"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>

        <IconButton
            class="admin-header__icon"
            text="password"
            emitMessage="modal"
            @modal="() => handleModal('password')"
            >>
            <template #icon>
                <font-awesome-icon icon="house" />
            </template>
        </IconButton>

        <IconButton
            class="admin-header__icon"
            text="로그아웃"
            emitMessage="logout"
            @logout="handlLogout"
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
