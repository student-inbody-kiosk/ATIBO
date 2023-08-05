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

// ckeditor config
const ckeditorConfig = {
    default: {
        width: '100%',
        height: '100%',
    },
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <VError v-else-if="isError" />
    <section v-else-if="gym">
        <form class="gym-update-description" @submit.prevent="handleSubmit">
            <div class="gym-update-description__header">
                <VInput
                    id="admin-gym-description"
                    label="기구 이름"
                    name="name"
                    :value="gym.name"
                    size="md" />
                <VButton text="글 저장" type="submit" color="admin-primary" />
            </div>
            <div class="gym-update-description__ckeditor">
                <ckeditor
                    :editor="ClassicEditor"
                    v-model="gym.description"
                    :config="ckeditorConfig" />
            </div>
        </form>
    </section>
</template>

<style lang="scss">
@import '@/styles/ckeditor.scss';

.gym-update-description {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    gap: 1rem;
    width: 100%;
    height: 100%;
}

.gym-update-description__header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
}

.gym-update-description__ckeditor {
    overflow: auto;
}
</style>
