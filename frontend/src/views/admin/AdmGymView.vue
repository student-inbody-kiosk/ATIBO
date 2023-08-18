<script setup lang="ts">
import { ref } from 'vue';
import VButton from '@/components/common/VButton.vue';
import TheModal from '@/components/common/TheModal.vue';
import GymList from '@/components/admin/gym/GymList.vue';
import GymCreateModal from '@/components/admin/gym/GymCreateModal.vue';

// handle modal
const isModalOpen = ref(false);

const handleOpenModal = function openGymCreateModal() {
    isModalOpen.value = true;
};

const handleCloseModal = function closeGymCreateModal() {
    isModalOpen.value = false;
};
</script>

<template>
    <div class="gym-view">
        <div class="gym-view__header">
            <RouterLink :to="{ name: 'admin-main' }">
                <VButton text="뒤로" color="gray" />
            </RouterLink>
            <h1 class="gym-view__header__title">운동기구 목록</h1>
            <VButton text="추가" color="green" @click="handleOpenModal" />
        </div>
        <GymList />
    </div>
    <!-- modal -->
    <TheModal
        v-if="isModalOpen"
        color="admin-secondary"
        @close-modal="handleCloseModal">
        <GymCreateModal />
    </TheModal>
</template>

<style lang="scss">
.gym-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-auto-rows: auto minmax(0, 1fr);
    height: 100%;
    width: 100%;
}

.gym-view__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    margin: 0 0 1.5rem 0;
}

.gym-view__header__title {
    font-size: 1.5rem;
    font-weight: 600;
    text-align: center;
}

.gym-view__header__button {
    position: absolute;
    top: 0;
    right: 0;
}
</style>
