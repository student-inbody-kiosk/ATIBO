<script setup lang="ts">
import services from '@/apis/services';
import KioGymListItem from './KioGymListItem.vue';

// Get gym data asynchronously
const gyms = await services.getGyms();
</script>

<template>
    <ul class="kiosk-gym-list">
        <li v-for="gym in gyms" :key="gym.id">
            <KioGymListItem :gym="gym" />
        </li>
    </ul>
</template>

<style lang="scss">
.kiosk-gym-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-auto-rows: minmax(170px, 190px);
    justify-items: center;
    align-items: center;
    overflow-y: auto;
    height: 100%;
    width: 100%;
}

.kiosk-gym-list li {
    height: 90%;
    width: 90%;
}
</style>
