<script setup lang="ts">
import IconButton from '@/components/common/IconButton.vue';

defineProps<{
    title: string;
    leftUrl: string;
    rightUrl: string;
}>();
</script>

<template lang="">
    <div class="kiosk-header">
        <IconButton
            class="kiosk-header__icon--left"
            text="버튼텍스트"
            :url="leftUrl">
            <template #icon>
                <font-awesome-icon icon="circle-arrow-left" />
            </template>
        </IconButton>

        <span class="kiosk-header__title">{{ title }} </span>

        <IconButton
            class="kiosk-header__icon--right"
            text="버튼텍스트"
            :url="rightUrl">
            <template #icon>
                <slot name="icon"></slot>
            </template>
        </IconButton>
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
