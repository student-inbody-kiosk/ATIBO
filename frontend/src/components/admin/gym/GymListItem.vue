<script setup lang="ts">
import type { GymSimple } from '@/types/gyms.interface';

defineProps<{
    gym: GymSimple;
}>();
</script>

<template>
    <RouterLink :to="{ name: 'admin-gym-detail', params: { gymId: gym.id } }">
        <div class="gym-list-item">
            {{ gym.name }}
        </div>
    </RouterLink>
</template>

<style lang="scss">
.gym-list-item {
    font-size: 2rem;
}
</style>
