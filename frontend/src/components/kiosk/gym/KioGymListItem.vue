<script setup lang="ts">
import type { GymSimple } from '@/types/gyms.interface';

defineProps<{
    gym: GymSimple;
}>();
</script>

<template>
    <RouterLink
        class="kiosk-gym-list-item--anchor"
        :to="{ name: 'kiosk-gym-detail', params: { gymId: gym.id } }">
        <article class="kiosk-gym-list-item">
            <h2>
                {{ gym.name }}
            </h2>
        </article>
    </RouterLink>
</template>

<style lang="scss">
.kiosk-gym-list-item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    padding: 0.5em;
    border-radius: 1em;
    background-color: transparentize($white, 0.4);
    box-shadow: 0 3px 3px 2px rgba(0, 0, 0, 0.1);
    font-size: 2rem;
    font-weight: 600;
    text-align: center;
    transition: transform 0.2s ease-in-out;
}

.kiosk-gym-list-item:hover {
    transform: scale(1.02);
}

.kiosk-gym-list-item--anchor {
    color: inherit;
    text-decoration: none;
}
</style>
