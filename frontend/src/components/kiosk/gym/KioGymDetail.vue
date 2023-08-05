<script setup lang="ts">
import type { GymDetail } from '@/types/gyms.interface';

const props = defineProps<{
    gym: GymDetail;
}>();
</script>

<template>
    <article class="kiosk-gym-detail">
        <ul class="kiosk-gym-detail__image">
            <li v-for="image in gym.imageSet" :key="image.id">
                <img :src="image.image" />
            </li>
        </ul>
        <p v-html="gym.description" />
    </article>
</template>

<style lang="scss">
.kiosk-gym-detail {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 20rem 1fr;
    overflow-y: auto;
    height: 100%;
    font-size: 2rem;
}

.kiosk-gym-detail::-webkit-scrollbar {
    display: none;
}

.kiosk-gym-detail__image {
    display: flex;
    gap: 1rem;
    overflow-x: auto;
    width: 100%;
}

.kiosk-gym-detail__image img {
    height: 18rem;
}
</style>
