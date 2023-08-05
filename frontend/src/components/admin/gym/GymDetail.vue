<script setup lang="ts">
import { ref, onBeforeMount } from 'vue';
import services from '@/apis/services';
import VError from '@/components/common/VError.vue';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import type { Gym, GymImage } from '@/types/gyms.interface';

const props = defineProps<{
    gymId: number;
}>();

/* Get gym data asynchrnously */
const isGymLoading = ref(false);
const isGymError = ref(false);
const gym = ref<Gym>();

const getGym = function () {
    isGymLoading.value = true;
    services
        .getGym(props.gymId)
        .then((res) => {
            gym.value = res;
            isGymLoading.value = false;
        })
        .catch((err) => {
            isGymError.value = true;
        });
};

onBeforeMount(() => {
    getGym();
});

/* Get gym image data asynchrnously */
const isGymImagesLoading = ref(false);
const isGymImagesError = ref(false);
const gymImages = ref<GymImage[]>();

const getGymImages = function () {
    isGymImagesLoading.value = true;
    services
        .getGymImages(props.gymId)
        .then((res) => {
            gymImages.value = res;
            isGymImagesLoading.value = false;
        })
        .catch((err) => {
            isGymImagesError.value = true;
        });
};

onBeforeMount(() => {
    getGymImages();
});
</script>

<template>
    <VLoading v-if="isGymLoading || isGymImagesLoading" color="admin-primary" />
    <VError v-else-if="isGymError || isGymImagesError" />
    <article v-else-if="gym" class="gym-detail">
        <section class="gym-detail__content">
            <div class="gym-detail__title">[ {{ gym.name }} ]</div>
            <div
                class="ck-editor gym-detail__description"
                v-html="gym.description" />
        </section>
        <section class="gym-detail__image">
            <ul class="gym-detail__image__list">
                <li v-for="image in gymImages" :key="image.id">
                    <img :src="image.image" />
                </li>
            </ul>
        </section>
    </article>
</template>

<style lang="scss">
@import '@/styles/ckeditor.scss';

.gym-detail {
    display: grid;
    grid-template-columns: minmax(0, 1fr) 300px;
    grid-template-rows: minmax(0, 1fr);
    column-gap: 1rem;
    width: 100%;
    height: 100%;
}

.gym-detail__content {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    gap: 1rem;
    width: 100%;
    height: 100%;
    padding: 1rem;
    background-color: $white;
}

.gym-detail__title {
    font-size: 1.5rem;
    font-weight: 600;
    text-align: center;
}

.gym-detail__description {
    overflow-y: auto;
}

.gym-detail__image {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: minmax(0, 1fr);
    row-gap: 1rem;
}

.gym-detail__image__list {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    gap: 1rem;
}

.gym-detail__image__list img {
    width: 100%;
    height: auto;
}
</style>
