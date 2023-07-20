<script setup lang="ts">
defineProps<{
    title: string;
}>();
</script>

<template lang="">
    <div class="kiosk-header">
        <span class="kiosk-header__icon"
            ><font-awesome-icon :icon="['fas', 'circle-arrow-left']"
        /></span>
        <span class="kiosk-header__title">{{ title }} </span>
        <span class="kiosk-header__icon"></span>
    </div>
</template>

<style lang="scss" scoped></style>
