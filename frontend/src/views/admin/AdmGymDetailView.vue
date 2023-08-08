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
            <RouterLink :to="{ name: 'admin-gym' }">
                <VButton text="돌아가기" color="gray" />
            </RouterLink>
            <h1 class="gym-detail-view__title">운동기구 상세</h1>
            <RouterLink
                :to="{ name: 'admin-gym-update', params: { gymId: gymId } }">
                <VButton text="수 정" color="admin-primary" />
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
    width: 100%;
    height: 100%;
}

.gym-detail-view__header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    position: relative;
    margin: 0 0 1.5rem 0;
}

.gym-detail-view__title {
    font-size: 1.5rem;
    font-weight: 600;
    text-align: center;
}
</style>
