<script setup lang="ts">
import type { GymSimple } from '@/types/gyms.interface';

defineProps<{
    gym: GymSimple;
}>();
</script>

<template>
    <RouterLink :to="{ name: 'kiosk-gym-detail', params: { gymId: gym.id } }">
        <div class="kiosk-gym-list-item">
            {{ gym.name }}
        </div>
    </RouterLink>
</template>

<style lang="scss">
.kiosk-gym-item {
    font-size: 2rem;
}
</style>
