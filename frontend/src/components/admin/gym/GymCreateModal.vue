<script setup lang="ts">
import { useRouter } from 'vue-router';
import services from '@/apis/services';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import { useAxios } from '@/hooks/useAxios';

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
    formData.append('description', '');

    createGym(formData).then((res) => {
        router.push({ name: 'admin-gym-update', params: { gymId: res.id } });
    });
};
</script>

<template>
    <div class="gym-create-modal">
        <VLoading v-if="isLoading" color="admin-primary" />
        <div v-else>
            <p class="gym-create-modal__title">운동기구 이름</p>
            <form
                class="gym-create-modal__content"
                @submit.prevent="handleSubmit">
                <VInput
                    id="gym-create-name"
                    name="name"
                    text-align="center"
                    type="text"
                    size="md" />
                <VButton text="생성" color="green" type="submit" />
            </form>
        </div>
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
