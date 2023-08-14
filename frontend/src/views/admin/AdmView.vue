<script setup lang="ts">
import { ref, computed, watchEffect } from 'vue';
import { useRoute, RouterView } from 'vue-router';
import AdmHeader from '@/components/admin/AdmHeader.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import TheModal from '@/components/common/TheModal.vue';
import SignupForm from '@/components/admin/SignupForm.vue';
import PwResetForm from '@/components/admin/accounts/PwResetForm.vue';
import AdmLayout from '@/layouts/AdmLayout.vue';
import { useQueryStore } from '@/stores/query.store';

const route = useRoute();
// Compute whether the current page is index page, based on the current route name
const isIndexPage = computed<Boolean>(() => {
    return route.name === 'admin-index' ? true : false;
});

// reset query store when the user loads the admin main page
const queryStore = useQueryStore();
watchEffect(() => {
    const routeName = route.name;
    if (routeName === 'admin-main') {
        queryStore.$reset;
    }
});

/* Manage modal */
const isSignupModalOpen = ref(false);
const isPwResetModalOpen = ref(false);

const handleCloseModal = function closeModal() {
    isSignupModalOpen.value = false;
    isPwResetModalOpen.value = false;
};
</script>

<template>
    <AdmLayout>
        <template #admin-header>
            <div v-if="isIndexPage" class="admin-index-header">
                <VIconButton
                    text="키오스크"
                    @click="$router.push({ name: 'kiosk-index' })">
                    <font-awesome-icon icon="house" size="2x" />
                </VIconButton>
                <div class="admin-index-header__right-buttons">
                    <VIconButton
                        text="비밀번호 찾기"
                        @click="isPwResetModalOpen = true">
                        <font-awesome-icon icon="lock" size="2x" />
                    </VIconButton>
                    <VIconButton
                        text="회원가입"
                        @click="isSignupModalOpen = true">
                        <font-awesome-icon icon="user-plus" size="2x" />
                    </VIconButton>
                </div>
            </div>
            <AdmHeader v-else />
        </template>

        <template #admin-main>
            <RouterView />
            <TheModal
                color="admin-secondary"
                v-if="isSignupModalOpen || isPwResetModalOpen"
                @close-modal="handleCloseModal">
                <SignupForm
                    v-if="isSignupModalOpen"
                    @success="handleCloseModal" />
                <PwResetForm v-else @success="handleCloseModal" />
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

.admin-index-header__right-buttons {
    display: flex;
    align-items: center;
    gap: 3rem;
}
</style>
