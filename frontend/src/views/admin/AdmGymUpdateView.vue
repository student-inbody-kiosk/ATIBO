<script setup lang="ts">
import { useRoute } from 'vue-router';
import GymUpdate from '@/components/admin/gym/GymUpdate.vue';
import VButton from '@/components/common/VButton.vue';

const route = useRoute();
const gymId = Number(route.params.gymId);
</script>

<template>
    <div class="gym-update-view">
        <div class="gym-update-view__header">
            <h1 class="gym-update-view__title">운동기구 수정</h1>
            <RouterLink
                class="gym-update-view__button"
                :to="{ name: 'admin-gym-detail', params: { gymId: gymId } }">
                <VButton text="뒤로" color="gray" />
            </RouterLink>
        </div>
        <GymUpdate :gymId="gymId" />
    </div>
</template>

<style lang="scss">
.gym-update-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    width: 100%;
    height: 100%;
}

.gym-update-view__header {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    margin: 0 0 1.5rem 0;
}

.gym-update-view__title {
    font-size: 1.5rem;
    font-weight: 600;
    text-align: center;
}

.gym-update-view__button {
    position: absolute;
    top: 0;
    left: 0;
}
</style>
