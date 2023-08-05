<script setup lang="ts">
import { onMounted } from 'vue';
import services from '@/apis/services';

const props = defineProps<{
    gymId: number;
}>();

const emit = defineEmits<{
    (e: 'get-gym-name', name: string): void;
}>();

// Update Kio Header
onMounted(() => {
    emit('get-gym-name', gym.name);
});

// Get gym detail data asynchronously
const gym = await services.getGym(props.gymId);
const gymImages = await services.getGymImages(props.gymId);
</script>

<template>
    <article class="kiosk-gym-detail">
        <ul class="kiosk-gym-detail__image">
            <li v-for="image in gymImages" :key="image.id">
                <img :src="image.image" />
            </li>
        </ul>
        <div>
            <div
                class="ck-editor kiosk-gym-detail__description"
                v-html="gym.description" />
        </div>
    </article>
</template>

<style lang="scss">
@import '@/styles/ckeditor.scss';

.kiosk-gym-detail {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 20rem 1fr;
    row-gap: 1rem;
    overflow-y: auto;
    height: 100%;
    padding: 1rem;
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

.kiosk-gym-detail__description {
    padding: 1rem;
    border-radius: 0.5em;
    background-color: white;
}
</style>
