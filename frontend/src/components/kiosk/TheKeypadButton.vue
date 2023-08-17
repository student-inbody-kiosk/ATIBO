<script setup lang="ts">
defineProps<{
    text: string;
}>();

defineEmits<{
    (e: 'click'): void;
}>();

const handleMousedown = function onMousedownAddClass(event: TouchEvent) {
    const button = event.target as HTMLButtonElement;
    button.classList.add('touch');
};

const handleMouseup = function onMouseupremoveClass(event: TouchEvent) {
    const button = event.target as HTMLButtonElement;
    button.classList.remove('touch');
};
</script>

<template lang="">
    <button
        class="keypad-button"
        @click="$emit('click')"
        @mousedown="handleMousedown"
        @touchstart="handleMousedown"
        @mouseup="handleMouseup"
        @touchend="handleMouseup">
        {{ text }}
    </button>
</template>

<style lang="scss">
.keypad-button {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: $kiosk-primary;
    border-radius: 1rem;
    color: $white;
    font-size: 4vh;
    font-weight: 700;
}

.keypad-button.touch {
    background-color: $kiosk-deep-primary;
}
</style>
