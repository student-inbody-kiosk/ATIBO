<script setup lang="ts">
import { onMounted } from 'vue';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import VError from '@/components/common/VError.vue';
import VLoading from '@/components/common/VLoading.vue';
import GymListItem from '@/components/admin/gym/GymListItem.vue';
import type { GymSimple } from '@/types/gyms.interface';

const {
    fetchData: getGymList,
    isLoading,
    isError,
    response: gyms,
} = useAxios<GymSimple[]>([], services.getGyms);

onMounted(() => {
    getGymList();
});
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <VError v-else-if="isError" />
    <ul v-else-if="gyms" class="gym-list">
        <li v-for="gym in gyms" :key="gym.id">
            <GymListItem :gym="gym" />
        </li>
    </ul>
</template>

<style lang="scss">
.gym-list {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: repeat(4, 1fr);
    grid-auto-rows: auto;
    justify-items: center;
    align-items: center;
    overflow-y: auto;
    height: 100%;
    width: 100%;
}

.gym-list li {
    height: 90%;
    width: 90%;
}
</style>
