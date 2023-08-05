<script setup lang="ts">
import { ref, onBeforeMount } from 'vue';
import services from '@/apis/services';
import GymListItem from '@/components/admin/gym/GymListItem.vue';
import VError from '@/components/common/VError.vue';
import VLoading from '@/components/common/VLoading.vue';
import type { GymSimple } from '@/types/gyms.interface';

// get gym list data
const isLoading = ref(false);
const isError = ref(false);
const gyms = ref<GymSimple[]>([]);

const getGymList = function () {
    isLoading.value = true;
    services
        .getGyms()
        .then((res) => {
            gyms.value = res;
            isLoading.value = false;
        })
        .catch(() => {
            isError.value = true;
        });
};

onBeforeMount(() => {
    getGymList();
});
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <VError v-else-if="isError" />
    <ul v-else-if="gyms" class="gym-list">
        <li>
            <GymListItem v-for="gym in gyms" :key="gym.id" :gym="gym" />
        </li>
    </ul>
</template>

<style lang="scss">
.gym-list {
    display: grid;
    grid-template-columns: 1fr, 1fr;
    grid-auto-rows: auto;
}
</style>
