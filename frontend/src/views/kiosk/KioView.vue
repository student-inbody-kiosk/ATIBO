<script setup lang="ts">
import { RouterView } from 'vue-router';
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import { onErrorCaptured } from 'vue';
import { useRoute } from 'vue-router';
import { ref, watchEffect } from 'vue';
import VLoading from '@/components/common/VLoading.vue';

const route = useRoute();
const title = ref('');
const routeName = ref('');

const handleUpdateHeader = function allocateTitleAndRouteName(data: {
    title: string;
    routeName: string;
}) {
    title.value = data.title;
    routeName.value = data.routeName;
};

onErrorCaptured((e: Error) => {
    return false;
});
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <VIconButton
                v-if="route.name === 'kiosk-index'"
                text="관리자"
                @click="$router.push({ name: 'admin-index' })">
                <font-awesome-icon icon="user-lock" />
            </VIconButton>
            <KioHeader v-else :title="title" :routeName="routeName" />
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
        opacity 0.5s ease,
        transform 0.5s ease;
}

.kiosk-enter-from,
.kiosk-leave-to {
    transform: translateY(20px);
    opacity: 0;
}
</style>
