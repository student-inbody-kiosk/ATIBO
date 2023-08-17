<script setup lang="ts">
import { useRouter } from 'vue-router';
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import { useAxios } from '@/hooks/useAxios';
import { toastTopErrorMessage } from '@/utils/toastManager';

const router = useRouter();

/* Create gym & if success navgiate to update page */
const { fetchData: createGym, isLoading } = useAxios<null>(
    null,
    services.createGym
);

const handleSubmit = function onHandleCreateGym(event: Event) {
    const form = event.target as HTMLFormElement;
    if (!form) return;

    const formData = new FormData(form);
    const name = (formData.get('name') as string) || '';
    if (name.length < 2) {
        toastTopErrorMessage('운동기구 이름은 2글자 이상 입력해주세요');
        return;
    }
    const description = '';

    const data = {
        name,
        description,
    };

    createGym(data).then((res) => {
        router.push({ name: 'admin-gym-update', params: { gymId: res.id } });
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <div v-else class="gym-create-modal">
        <p class="gym-create-modal__title">운동기구 이름</p>
        <form class="gym-create-modal__content" @submit.prevent="handleSubmit">
            <VInput
                id="gym-create-name"
                name="name"
                text-align="center"
                type="text"
                size="md" />
            <VButton text="생성" color="green" type="submit" />
        </form>
    </div>
</template>

<style lang="scss">
.gym-create-modal {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
    background-color: $admin-secondary;
}

.gym-create-modal__title {
    font-size: 1.5rem;
    font-weight: 600;
}

.gym-create-modal__content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
}
</style>
