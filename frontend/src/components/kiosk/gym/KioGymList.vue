<script setup lang="ts">
import KioGymListItem from './KioGymListItem.vue';
import type { GymSimple } from '@/types/gyms.interface';

defineProps<{
    gyms: GymSimple[];
}>();
</script>

<template>
    <ul class="kiosk-gym-list">
        <li>
            <KioGymListItem v-for="gym in gyms" :key="gym.id" :gym="gym" />
        </li>
    </ul>
</template>

<style lang="scss">
.kiosk-gym-list {
    display: grid;
    grid-template-columns: 1fr, 1fr;
    grid-auto-rows: auto;
}
</style>
