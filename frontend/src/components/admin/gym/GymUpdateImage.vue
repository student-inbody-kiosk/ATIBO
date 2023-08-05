<script setup lang="ts">
import { ref, onBeforeMount } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import type { GymImage } from '@/types/gyms.interface';

const props = defineProps<{
    gymId: number;
}>();

/* Get gym data aysnchronously */
const isLoading = ref(false);
const isError = ref(false);
const gymImages = ref<GymImage[]>([]);
const gymImageUrls = ref<string[]>([]);

const getGymImages = function (gymId: number) {
    isLoading.value = true;
    services
        .getGymImages(gymId)
        .then((res) => {
            gymImages.value = res;
            for (const image of res) {
                if (typeof image.image === 'string') {
                    gymImageUrls.value.push(image.image);
                }
            }
            isLoading.value = false;
        })
        .catch(() => {
            isError.value = true;
        });
};

onBeforeMount(() => {
    getGymImages(props.gymId);
});

/* Managing multiple image update */

// Add gym images
const handleChangeAdd = function appendNewImages() {
    const fileInput = event?.target as HTMLInputElement;
    if (!fileInput) return;

    const files = fileInput.files;
    if (!files) return;

    for (const file of Array.from(files)) {
        const newImage = {
            image: file,
        };
        gymImages.value.push(newImage);
        gymImageUrls.value.push(URL.createObjectURL(file));
    }

    fileInput.value = ''; // reset the input
};

// Delete gym image
const handleClickDelete = function deleteImages(index: number) {
    gymImages.value.splice(index, 1);
    gymImageUrls.value.splice(index, 1);
};

// Update gym images data asynchronously
const handleClick = function updateGymImages() {
    services.updateGymImages(props.gymId, gymImages.value);
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <VError v-else-if="isError" />
    <section class="gym-update-image">
        <div class="gym-update-image__buttons">
            <div class="gym-update-image__add-button">
                <VButton text="사진 추가" color="green" size="xs" />
                <VInput
                    id="admin-gym-images"
                    name="images"
                    type="file"
                    accept="image/*"
                    :multiple="true"
                    @change="handleChangeAdd" />
            </div>
            <VButton
                text="사진 저장"
                color="admin-primary"
                size="xs"
                @click="handleClick" />
        </div>
        <ul class="gym-update-image__list">
            <li
                class="gym-update-image__list__item"
                v-for="(gymImageUrl, index) in gymImageUrls"
                :key="gymImageUrl">
                <VIconButton
                    class="gym-update-image__list__delete-button"
                    @click="handleClickDelete(index)">
                    <FontAwesomeIcon
                        icon="circle-minus"
                        size="xl"
                        color="#ce0000" />
                </VIconButton>
                <img :src="gymImageUrl" />
            </li>
        </ul>
    </section>
</template>

<style lang="scss">
@import '@/styles/ckeditor.scss';

.gym-update-image {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    row-gap: 1rem;
}

.gym-update-image__buttons {
    display: flex;
    justify-content: space-between;
}

// hide the <input type="file"/> behind the button
.gym-update-image__add-button {
    position: relative;
    display: inline-block;
    cursor: pointer;

    div:nth-child(2) {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        opacity: 0;
    }
}

.gym-update-image__list {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    gap: 1rem;
}

.gym-update-image__list img {
    width: 100%;
    height: auto;
}

.gym-update-image__list__item {
    position: relative;
}

.gym-update-image__list__delete-button {
    position: absolute;
    top: 0.5rem;
    right: 0.5rem;
}
</style>
