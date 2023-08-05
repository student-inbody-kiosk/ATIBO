<script setup lang="ts">
import { useRoute } from 'vue-router';
import GymUpdate from '@/components/admin/gym/GymUpdate.vue';

const route = useRoute();
const gymId = Number(route.params.gymId);
</script>

<template>
    <div class="gym-update-view">
        <GymUpdate :gymId="gymId" />
    </div>
</template>

<style lang="scss">
.gym-update-view {
    padding: 1rem;
    width: 100%;
    height: 100%;
}
</style>
