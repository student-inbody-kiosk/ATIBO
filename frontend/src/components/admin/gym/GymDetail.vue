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
    getGym();
    getGymImages();
});
</script>

<template>
    <VLoading v-if="isGymLoading || isGymImagesLoading" color="admin-primary" />
    <VError v-else-if="isGymError || isGymImagesError" />
    <div v-else-if="gym">
        <RouterLink
            :to="{ name: 'admin-gym-update', params: { gymId: gym.id } }">
            <VButton text="수정" color="admin-primary" />
        </RouterLink>
        <article class="gym-detail">
            <section class="gym-detail__description">
                <div>{{ gym.name }}</div>
                <p v-html="gym.description" />
            </section>
            <section class="gym-detail__image">
                <ul class="gym-detail__image__list">
                    <li v-for="image in gymImages" :key="image.id">
                        <img :src="image.image" />
                    </li>
                </ul>
            </section>
        </article>
    </div>
</template>

<style lang="scss">
.gym-detail {
    display: grid;
    grid-template-columns: minmax(0, 1fr) 300px;
    grid-template-rows: minmax(0, 1fr);
    height: 100%;
}

.gym-detail::-webkit-scrollbar {
    display: none;
}

.gym-detail__description {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    gap: 1rem;
}

.gym-detail__image {
}

.gym-detail__image__list {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    gap: 1rem;
}

.gym-detail__image img {
    width: 100%;
    height: auto;
}
</style>
