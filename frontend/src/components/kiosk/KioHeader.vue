<script setup lang="ts">
import VIconButton from '@/components/common/VIconButton.vue';

defineProps<{
    title: string;
    routeName: string;
}>();
</script>

<template lang="">
    <div class="kiosk-header">
        <div class="kiosk-header__button">
            <VIconButton size="lg" @click="$router.push({ name: routeName })">
                <font-awesome-icon icon="circle-arrow-left" size="2x" />
            </VIconButton>
        </div>
        <h1 class="kiosk-header__title">{{ title }}</h1>
    </div>
</template>

<style lang="scss">
.kiosk-header {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    margin: 1rem 2rem;
}

.kiosk-header__button {
    position: absolute;
    top: 0;
    left: 0;
}

.kiosk-header__title {
    font-size: 2.5rem;
    font-weight: 600;
}
</style>
