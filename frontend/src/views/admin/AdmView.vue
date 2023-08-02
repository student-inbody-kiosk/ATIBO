<script setup lang="ts">
import { RouterView } from 'vue-router';
import AdmLayout from '@/components/admin/AdmLayout.vue';
import AdmHeader from '@/components/admin/AdmHeader.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import TheModal from '@/components/common/TheModal.vue';

import { useRoute } from 'vue-router';
import { ref, watchEffect } from 'vue';

const route = useRoute();
const isIndexPage = ref(false);
const isEmailModalOpen = ref(false);
const isPasswordModalOpen = ref(false);

watchEffect(() => {
    // show different components based on the route name
    if (route.name === 'admin-index') {
        isIndexPage.value = true;
    } else {
        isIndexPage.value = false;
    }
});

const handleModalOpen = function openModal(message: string) {
    if (message === 'email') {
        isEmailModalOpen.value = true;
        return;
    }
    isPasswordModalOpen.value = true;
};

const handleModalClose = function closeModal() {
    isEmailModalOpen.value = false;
    isPasswordModalOpen.value = false;
};
</script>

<template>
    <AdmLayout>
        <template #admin-header>
            <VIconButton
                v-if="isIndexPage"
                text="키오스크"
                @click="$router.push({ name: 'kiosk-index' })">
                <font-awesome-icon icon="house" />
            </VIconButton>
            <AdmHeader v-else @open-modal="handleModalOpen" />
        </template>

        <template #admin-main>
            <RouterView />
            <TheModal
                v-if="isEmailModalOpen || isPasswordModalOpen"
                @close-modal="handleModalClose">
                <div v-if="isEmailModalOpen">Email Form</div>
                <div v-if="isPasswordModalOpen">passwordform</div>
            </TheModal>
        </template>
    </AdmLayout>
</template>

<style lang="scss" scoped></style>
