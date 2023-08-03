<script setup lang="ts">
import { RouterView } from 'vue-router';
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import { onErrorCaptured } from 'vue';
import { useRoute } from 'vue-router';
import { ref, inject } from 'vue';
import type { Header } from '@/types/app.interface';
import VLoading from '@/components/common/VLoading.vue';

const route = useRoute();
const toast = inject('toast');

const header = ref<Header>({
    title: '',
    routeName: '',
});

const handleUpdateHeader = function allocateTitleAndRouteName(data: Header) {
    header.value = data;
};

onErrorCaptured((err: Error) => {
    return false;
});
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <VIconButton
                v-if="route.name === 'kiosk-index'"
                text="관리자"
                size="lg"
                @click="$router.push({ name: 'admin-index' })">
                <font-awesome-icon icon="user-lock" size="2x" />
            </VIconButton>
            <KioHeader
                v-else
                :title="header.title"
                :routeName="header.routeName" />
        </template>
        <template #kiosk-main>
            <RouterView v-slot="{ Component }">
                <template v-if="Component">
                    <Transition name="kiosk">
                        <Suspense>
                            <!-- main content -->
                            <component
                                :is="Component"
                                @update-header="handleUpdateHeader"></component>

                            <!-- loading state -->
                            <template #fallback>
                                <VLoading color="kiosk-primary" size="lg" />
                            </template>
                        </Suspense>
                    </Transition>
                </template>
            </RouterView>
        </template>
    </KioLayout>
</template>

<style lang="scss">
.kiosk-enter-active,
.kiosk-leave-active {
    transition:
        opacity 0.3s ease,
        transform 0.3s ease;
}

.kiosk-enter-from,
.kiosk-leave-to {
    transform: translateY(20px);
    opacity: 0;
}
</style>
