<script setup lang="ts">
defineProps<{
    text?: string;
    url: string;
}>();
</script>

<template>
    <span class="icon-button" @click="this.$router.push({ name: url })">
        <slot name="icon" />
        <span>{{ text }}</span>
    </span>
</template>

<style lang="scss">
.icon-button {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
