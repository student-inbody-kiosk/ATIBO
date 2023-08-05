<script setup lang="ts">
import { useRoute } from 'vue-router';
import GymDetailVue from '@/components/admin/gym/GymDetail.vue';
import VButton from '@/components/common/VButton.vue';

const route = useRoute();
const gymId = Number(route.params.gymId);
</script>

<template>
    <div class="gym-detail-view">
        <div class="gym-detail-view__header">
            <h1 class="gym-detail-view__title">운동기구 수정</h1>
            <RouterLink
                class="gym-detail-view__button"
                :to="{ name: 'admin-gym-update', params: { gymId: gymId } }">
                <VButton text="수정" color="admin-primary" />
            </RouterLink>
        </div>
        <GymDetailVue :gymId="gymId" />
    </div>
</template>

<style lang="scss">
.gym-detail-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    padding: 1rem;
    width: 100%;
    height: 100%;
}

.gym-detail-view__header {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
}

.gym-detail-view__title {
    font-size: 2rem;
    font-weight: 600;
    text-align: center;
    margin: 0 0 1rem 0;
}

.gym-detail-view__button {
    position: absolute;
    top: 0;
    right: 0;
}
</style>
