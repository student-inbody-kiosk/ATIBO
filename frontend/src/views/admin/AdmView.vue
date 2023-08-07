<script setup lang="ts">
import { RouterView } from 'vue-router';
import AdmLayout from '@/components/admin/AdmLayout.vue';
import AdmHeader from '@/components/admin/AdmHeader.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import TheModal from '@/components/common/TheModal.vue';
import SignupForm from '@/components/admin/SignupForm.vue';
import { useRoute } from 'vue-router';
import { ref, watchEffect } from 'vue';

const route = useRoute();
const isIndexPage = ref(false);
const isSignupModalOpen = ref(false);
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

const handleModalClose = function closeModal() {
    isEmailModalOpen.value = false;
    isPasswordModalOpen.value = false;
    isSignupModalOpen.value = false;
};
</script>

<template>
    <AdmLayout>
        <template #admin-header>
            <div v-if="isIndexPage" class="admin-index-header">
                <VIconButton
                    text="키오스크"
                    @click="$router.push({ name: 'kiosk-index' })">
                    <font-awesome-icon icon="house" />
                </VIconButton>
                <VIconButton text="회원가입" @click="isSignupModalOpen = true">
                    <font-awesome-icon icon="user-plus" />
                </VIconButton>
            </div>
            <AdmHeader
                v-else
                @email="isEmailModalOpen = true"
                @password="isPasswordModalOpen = true" />
        </template>

        <template #admin-main>
            <RouterView />
            <TheModal
                color="admin-secondary"
                v-if="
                    isSignupModalOpen || isEmailModalOpen || isPasswordModalOpen
                "
                @close-modal="handleModalClose">
                <SignupForm
                    v-if="isSignupModalOpen"
                    @signup="isSignupModalOpen = false" />
                <div v-if="isEmailModalOpen">Email Form</div>
                <div v-if="isPasswordModalOpen">passwordform</div>
            </TheModal>
        </template>
    </AdmLayout>
</template>

<style lang="scss" scoped>
.admin-index-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}
</style>
