<script setup lang="ts">
import VIconButton from '@/components/common/VIconButton.vue';

defineProps<{
    title: string;
    routeName: string;
    routeParams?: object;
    routeQuery?: object;
}>();
</script>

<template lang="">
    <div class="kiosk-header">
        <h1 class="kiosk-header__title">{{ title }}</h1>
        <div class="kiosk-header__button">
            <RouterLink
                :to="{
                    name: routeName,
                    params: routeParams,
                    query: routeQuery,
                }">
                <VIconButton>
                    <font-awesome-icon icon="circle-arrow-left" size="3x" />
                </VIconButton>
            </RouterLink>
        </div>
    </div>
</template>

<style lang="scss">
.kiosk-header {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    margin: 1.5rem 2rem 1rem;
}

.kiosk-header__button {
    position: absolute;
    top: 0;
    left: 0;
}

.kiosk-header__title {
    font-size: 3rem;
    font-weight: 600;
}
</style>
