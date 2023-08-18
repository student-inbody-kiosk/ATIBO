<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import VError from '@/components/common/VError.vue';
import VLoading from '@/components/common/VLoading.vue';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import type { GymImage } from '@/types/gyms.interface';

const props = defineProps<{
    gymId: number;
}>();

/* Get gym data aysnchronously */
const {
    fetchData: getGymImages,
    isLoading,
    isError,
    response: gymImages,
} = useAxios<GymImage[]>([], services.getGymImages);

onMounted(() => {
    getGymImages(props.gymId);
});

/* Computed gymImageUrls based on gymIamges */

const gymImageUrls = computed<string[]>(() => {
    const imageUrls = [];
    for (const image of gymImages.value) {
        if (typeof image.image === 'string') {
            imageUrls.push(image.image);
        } else {
            imageUrls.push(URL.createObjectURL(image.image));
        }
    }
    return imageUrls;
});

/* Managing multiple image update */

// Add gym images asynchronously
const handleChangeAdd = function appendNewImages(target: HTMLInputElement) {
    const fileInput = target;
    if (!fileInput) return;

    const files = fileInput.files;
    if (!files) return;

    for (const file of Array.from(files)) {
        const newImage = {
            image: file,
        };
        gymImages.value.push(newImage);
    }

    fileInput.value = ''; // reset the input
};

// Delete gym image
const handleClickDelete = function deleteImages(index: number) {
    gymImages.value.splice(index, 1);
};

// Update gym images data asynchronously
const router = useRouter();
const handleClick = function updateGymImages() {
    services.updateGymImages(props.gymId, gymImages.value).then(() => {
        router.push({
            name: 'admin-gym-detail',
            params: { gymId: props.gymId },
        });
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <VError v-else-if="isError" />
    <section v-else-if="gymImages" class="gym-update-image">
        <div class="gym-update-image__buttons">
            <div class="gym-update-image__add-button">
                <VButton text="사진 추가" color="green" size="sm" />
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
                size="sm"
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
                <img :src="gymImageUrl" alt="운동 기구 이미지" />
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

    div:nth-child(1) {
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
