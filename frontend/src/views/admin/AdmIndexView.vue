<script setup lang="ts">
import LoginForm from '@/components/admin/accounts/LoginForm.vue';
</script>

<template>
    <div class="admin-index-view">
        <LoginForm />
    </div>
</template>

<style lang="scss" scoped>
.admin-index-view {
    align-self: center;
    padding: 3rem 3rem 2rem 3rem;
    border-radius: 1rem;
    background-color: $admin-tertiary;
}
</style>
