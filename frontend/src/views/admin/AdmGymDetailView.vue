<script setup lang="ts">
import { useRoute } from 'vue-router';
import GymDetailVue from '@/components/admin/gym/GymDetail.vue';

const route = useRoute();
const gymId = Number(route.params.gymId);
</script>

<template>
    <div class="gym-detail-view">
        <GymDetailVue :gymId="gymId" />
    </div>
</template>

<style lang="scss">
.gym-detail-view {
    padding: 1rem;
    height: 100%;
}
</style>
