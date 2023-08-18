<script setup lang="ts">
import VButton from '../common/VButton.vue';
import type { SchoolAccount } from '@/types/accounts.interface';

defineProps<{
    title: string;
    accountList: SchoolAccount[];
}>();

defineEmits<{
    (e: 'delete', userId: number): void;
    (e: 'accept', userId: number): void;
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
                    <div>
                        <p>{{ account.name }}</p>
                        <span>{{ account.email }}</span>
                    </div>
                    <div>소개 : {{ account.comment }}</div>
                </div>
                <VButton
                    v-if="account.isActive"
                    text="삭제"
                    color="red"
                    @click="$emit('delete', account.id)" />
                <VButton
                    v-else
                    text="승인"
                    color="green"
                    @click="$emit('accept', account.id)" />
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.account-container {
    min-width: 30rem;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
}
.account-container__title {
    background-color: $admin-tertiary;
    font-size: 1.1rem;
    padding: 0.7rem;
}

.account-list {
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
    display: grid;
    gap: 0.5rem;

    div:first-child {
        display: flex;
        gap: 1rem;

        p {
            font-weight: 600;
        }
    }
}
</style>
