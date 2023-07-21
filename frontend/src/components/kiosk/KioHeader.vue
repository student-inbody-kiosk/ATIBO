<script setup lang="ts">
defineProps<{
    title: string;
    leftUrl: string;
    rightUrl: string;
}>();
</script>

<template lang="">
    <div class="kiosk-header">
        <span
            class="kiosk-header__icon kiosk-header__icon--left"
            @click="$router.push({ name: leftUrl })"
            ><font-awesome-icon icon="circle-arrow-left"
        /></span>
        <span class="kiosk-header__title">{{ title }} </span>
        <span
            class="kiosk-header__icon kiosk-header__icon--right"
            @click="$router.push({ name: rightUrl })"
            ><slot name="icon"
        /></span>
    </div>
</template>

<style lang="scss">
.kiosk-header {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
}

.kiosk-header__title {
    text-align: center;
}

.kiosk-header__icon--right {
    display: flex;
    justify-content: flex-end;
}

.kiosk-header__icon {
    font-size: 2rem; // 수정필요
}
</style>
