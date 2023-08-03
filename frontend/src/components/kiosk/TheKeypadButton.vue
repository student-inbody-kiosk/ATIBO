<script setup lang="ts">
defineProps<{
    text: string;
}>();

defineEmits<{
    (e: 'click'): void;
}>();
</script>

<template lang="">
    <button class="keypad__button" @click="$emit('click')">
        {{ text }}
    </button>
</template>

<style lang="scss">
.keypad__button {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: $kiosk-primary;
    border-radius: 1rem;
    color: $white;
    font-size: 2.5rem;
    font-weight: 700;
}
</style>
