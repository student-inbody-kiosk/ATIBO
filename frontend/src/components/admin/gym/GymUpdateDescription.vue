<script setup lang="ts">
import { ref, onBeforeMount } from 'vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import type { Gym } from '@/types/gyms.interface';

const props = defineProps<{
    gymId: number;
}>();

// Get gym data aysnchronously
const isLoading = ref(false);
const isError = ref(false);
const gym = ref<Gym>();

const getGym = function (gymId: number) {
    isLoading.value = true;
    services
        .getGym(gymId)
        .then((res) => {
            gym.value = res;
            isLoading.value = false;
        })
        .catch(() => {
            isError.value = true;
        });
};

onBeforeMount(() => {
    getGym(props.gymId);
});

// update gym data
const handleSubmit = function updateGym(event: Event) {
    const form = event.target as HTMLFormElement;
    if (!form) return;

    const formData = new FormData(form);
    formData.append(
        'description',
        gym.value?.description ? gym.value.description : ''
    );

    services.updateGym(props.gymId, formData);
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <VError v-else-if="isError" />
    <section v-else-if="gym" class="gym-update-description">
        <form @submit.prevent="handleSubmit">
            <VButton text="글 저장" type="submit" color="admin-primary" />
            <VInput
                id="admin-gym-description"
                name="name"
                :value="gym.name"
                size="md" />
            <ckeditor
                :editor="ClassicEditor"
                v-model="gym.description"
                :config="{}" />
        </form>
    </section>
</template>

<style lang="scss">
@import '@/styles/ckeditor.scss';

.gym-update-description {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    gap: 1rem;
}
</style>
