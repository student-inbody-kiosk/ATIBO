<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import GymDetailVue from '@/components/admin/gym/GymDetail.vue';
import VButton from '@/components/common/VButton.vue';
import services from '@/apis/services';

const route = useRoute();
const gymId = Number(route.params.gymId);

/* Delete gym data */
const router = useRouter();
const handleClick = function deleteGym(event: Event) {
    if (!confirm('정말 삭제하시겠습니까? 되돌릴 수 없습니다')) return;
    services.deleteGym(gymId).then(() => {
        router.push({
            name: 'admin-gym',
        });
    });
};
</script>

<template>
    <div class="gym-detail-view">
        <div class="gym-detail-view__header">
            <RouterLink :to="{ name: 'admin-gym' }">
                <VButton text="뒤로" color="gray" />
            </RouterLink>
            <h1 class="gym-detail-view__title">운동기구 상세</h1>
            <div class="gym-detail-view__buttons">
                <RouterLink
                    :to="{
                        name: 'admin-gym-update',
                        params: { gymId: gymId },
                    }">
                    <VButton text="수정" color="admin-primary" />
                </RouterLink>
                <VButton text="삭제" color="red" @click="handleClick" />
            </div>
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
    align-items: center;
    position: relative;
    margin: 0 0 1.5rem 0;
}

.gym-detail-view__title {
    font-size: 1.5rem;
    font-weight: 600;
    text-align: center;
}

.gym-detail-view__buttons {
    display: flex;
    gap: 20px;
}
</style>
