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
            class="kiosk-header__icon--left"
            text="뒤로가기"
            @click="$router.push({ name: routeName })">
            <font-awesome-icon icon="circle-arrow-left" />
        </VIconButton>

        <span class="kiosk-header__title">{{ title }} </span>

        <VIconButton
            class="kiosk-header__icon--right"
            text="처음으로"
            @click="$router.push({ name: 'kiosk-index' })">
            <font-awesome-icon icon="house" />
        </VIconButton>
    </div>
</template>

<style lang="scss">
.kiosk-header {
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    grid-template-areas: 'a b b b c';
}

.kiosk-header__title {
    grid-area: b;
    text-align: center;
}

.kiosk-header__icon--left {
    grid-area: a;
}

.kiosk-header__icon--right {
    grid-area: c;
}
</style>
