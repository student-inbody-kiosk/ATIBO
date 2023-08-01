<script setup lang="ts">
import VIconButton from '@/components/common/VIconButton.vue';

defineProps<{
    title: string;
    routeName: string;
}>();
</script>

<template lang="">
    <div class="kiosk-header">
        <VIconButton
            text="뒤로가기"
            size="lg"
            @click="$router.push({ name: routeName })">
            <font-awesome-icon icon="circle-arrow-left" size="2x" />
        </VIconButton>

        <h1 class="kiosk-header__title">{{ title }}</h1>

        <VIconButton
            text="처음으로"
            size="lg"
            @click="$router.push({ name: 'kiosk-index' })">
            <font-awesome-icon icon="house" size="2x" />
        </VIconButton>
    </div>
</template>

<style lang="scss">
.kiosk-header {
    display: flex;
    justify-content: space-between;
    margin: 1rem 2rem;
}

.kiosk-header__title {
    font-size: 2rem;
    font-weight: 600;
}
</style>
