<script setup lang="ts">
defineProps<{
    text?: string;
    emitMessage: string;
}>();
</script>

<template>
    <button class="icon-button" @click="$emit(emitMessage)">
        <slot name="icon" />
        <span>{{ text }}</span>
    </button>
</template>

<style lang="scss">
.icon-button {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
