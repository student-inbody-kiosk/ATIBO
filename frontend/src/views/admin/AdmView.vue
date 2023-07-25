<script setup lang="ts">
import { RouterView } from 'vue-router';
import AdmLayout from '@/components/admin/AdmLayout.vue';
import AdmHeader from '@/components/admin/AdmHeader.vue';
import IconButton from '@/components/common/IconButton.vue';
import TheModal from '@/components/common/TheModal.vue';

import { useRoute } from 'vue-router';
import { ref, watchEffect } from 'vue';

const route = useRoute();
const isIndexPage = ref(false);
const isEmailModalOpen = ref(false);
const isPasswordModalOpen = ref(false);

watchEffect(() => {
    // TODO: based on the presence of tokens
    if (route.name === 'admin') {
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
            <IconButton
                v-show="isIndexPage"
                text="키오스크"
                emitMessage="routing"
                @routing="$router.push({ name: 'kiosk-index' })">
                <template #icon>
                    <font-awesome-icon icon="house" />
                </template>
            </IconButton>
            <AdmHeader v-show="!isIndexPage" @open-modal="handleModalOpen" />
        </template>

        <template #admin-main>
            <RouterView />
            <teleport to="#teleport">
                <TheModal
                    v-show="isEmailModalOpen || isPasswordModalOpen"
                    @close-modal="handleModalClose">
                    <template #modal-content>
                        <div v-show="isEmailModalOpen">Email Form</div>
                        <div v-show="isPasswordModalOpen">passwordform</div>
                    </template>
                </TheModal>
            </teleport>
        </template>
    </AdmLayout>
</template>

<style lang="scss" scoped></style>
