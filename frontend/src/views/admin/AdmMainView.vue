<script setup lang="ts">
import AdmLayout from '@/components/admin/AdmLayout.vue';
import AdmHeader from '@/components/admin/AdmHeader.vue';
import TheModal from '@/components/common/TheModal.vue';
import TheButton from '@/components/common/TheButton.vue';

import { ref } from 'vue';

const isEmailModalOpen = ref(false);
const isPasswordModalOpen = ref(false);

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
            <AdmHeader @open-modal="handleModalOpen" />
        </template>

        <template #admin-main>
            <div class="admin-main__buttons">
                <TheButton
                    text="학생 관리"
                    color="admin-primary"
                    size="lg"
                    emitMessage="student"
                    @student="$router.push({ name: 'admin-student' })" />
                <TheButton
                    text="출결 관리"
                    color="admin-primary"
                    size="lg"
                    emitMessage="attend"
                    @attend="$router.push({ name: 'admin-attend' })" />
                <TheButton
                    text="인바디 관리"
                    color="admin-primary"
                    size="lg"
                    emitMessage="inbody"
                    @inbody="$router.push({ name: 'admin-inbody' })" />
            </div>

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

<style lang="scss">
body {
    background-color: $white;
}

.admin-main__buttons {
    display: flex;
    flex-direction: column;
}
</style>
