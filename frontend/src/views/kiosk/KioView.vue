<script setup lang="ts">
import { ref, onErrorCaptured } from 'vue';
import { useRoute, RouterView } from 'vue-router';
import VLoading from '@/components/common/VLoading.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import KioLayout from '@/layouts/KioLayout.vue';
import type { Header, HeaderUpdate } from '@/types/app.interface';

const route = useRoute();

// data for KioHeader
const header = ref<Header>({
    title: '',
    routeName: '',
    routeQuery: {},
    routeParams: {},
});

// update KioHeader data
const handleUpdateHeader = function allocateTitleAndRouteName(
    data: HeaderUpdate
) {
    if (data.title) {
        header.value.title = data.title;
    }
    if (data.routeName) {
        header.value.routeName = data.routeName;
    }
    if (data.routeQuery) {
        header.value.routeQuery = data.routeQuery;
    }
    if (data.routeParams) {
        header.value.routeParams = data.routeParams;
    }
};

// Handle <Suspense> error
onErrorCaptured(() => {
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
                :route-name="header.routeName"
                :route-params="header.routeParams"
                :route-query="header.routeQuery" />
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
