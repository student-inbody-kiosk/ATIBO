<script setup lang="ts">
import type { GymSimple } from '@/types/gyms.interface';

defineProps<{
    gym: GymSimple;
}>();
</script>

<template>
    <RouterLink
        class="gym-list-item--anchor"
        :to="{ name: 'admin-gym-detail', params: { gymId: gym.id } }">
        <article class="gym-list-item">
            <h2>
                {{ gym.name }}
            </h2>
        </article>
    </RouterLink>
</template>

<style lang="scss">
.gym-list-item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    border-radius: 1em;
    background-color: transparentize($white, 0.4);
    box-shadow:
        inset 0 3px 5px rgba(255, 255, 255, 0.3),
        inset 0 -3px 5px rgba(0, 0, 0, 0.3),
        0 1px 2px 2px transparentize($black, 0.9);
    font-size: 1.5rem;
    font-weight: 600;
    transition: transform 0.2s ease-in-out;
}

.gym-list-item:hover {
    transform: scale(1.02);
}

.gym-list-item--anchor {
    color: inherit;
    text-decoration: none;
}
</style>
