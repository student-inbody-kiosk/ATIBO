<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import CKEditor from '@ckeditor/ckeditor5-vue';
import services from '@/apis/services';
import VError from '@/components/common/VError.vue';
import VLoading from '@/components/common/VLoading.vue';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import { useAxios } from '@/hooks/useAxios';
import { toastTopErrorMessage } from '@/utils/toastManager';
import type { Gym } from '@/types/gyms.interface';

const props = defineProps<{
    gymId: number;
}>();

/* Get gym data aysnchronously */
const {
    fetchData: getGym,
    isLoading,
    isError,
    response: gym,
} = useAxios<Gym>({}, services.getGym);

onMounted(() => {
    getGym(props.gymId);
});

/* Update gym data */
const router = useRouter();
const handleSubmit = function updateGym(event: Event) {
    const form = event.target as HTMLFormElement;
    if (!form) return;

    const formData = new FormData(form);
    const name = (formData.get('name') as string) || '';
    if (name.length < 2) {
        toastTopErrorMessage('운동기구 이름은 2글자 이상 입력해주세요');
        return;
    }
    const description = gym.value?.description ? gym.value.description : '';

    const data = {
        name,
        description,
    };

    services.updateGym(props.gymId, data).then(() => {
        router.push({
            name: 'admin-gym-detail',
            params: { gymId: props.gymId },
        });
    });
};

// ckeditor config & component
const ckeditorConfig = {};
const ckeditor = CKEditor.component;
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
                <VButton
                    text="글 저장"
                    type="submit"
                    color="admin-primary"
                    size="sm" />
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
