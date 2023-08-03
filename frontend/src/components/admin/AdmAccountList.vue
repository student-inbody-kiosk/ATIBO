<script setup lang="ts">
import VButton from '../common/VButton.vue';
import type { SchoolAccount } from '@/types/accounts.interface';

defineProps<{
    title: string;
    accountList: SchoolAccount[];
}>();
</script>

<template>
    <div class="account-container">
        <h3 class="account-container__title">{{ title }}</h3>
        <div class="account-list">
            <div
                class="account-list__item"
                v-for="account in accountList"
                :key="account.id">
                <div class="account-list__item__content">
                    <span>{{ account.name }}</span>
                    <span>{{ account.email }}</span>
                    <span>{{ account.comment }}</span>
                </div>
                <VButton
                    v-if="account.isActive"
                    text="삭제"
                    color="red"
                    @click="$emit('delete')" />
                <VButton
                    v-else
                    text="승인"
                    color="green"
                    @click="$emit('accept')" />
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.account-container__title {
    background-color: $admin-tertiary;
    font-size: 1.3rem;
    padding: 0.7rem;
}

.account-list {
    height: 100%;
    overflow-y: auto;
}

.account-list__item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    min-height: 2.8rem;
    padding: 0.5rem;
    background-color: $white;
    border-bottom: 0.1rem solid $admin-secondary;
}

.account-list__item__content {
    display: flex;
    gap: 0.5rem;
}
</style>
