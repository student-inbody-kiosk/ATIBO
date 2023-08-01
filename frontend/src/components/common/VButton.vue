<script setup lang="ts">
const props = defineProps<{
    text: string;
    color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
    size: 'sm' | 'md' | 'lg';
}>();

const emit = defineEmits<{
    (e: 'click'): void;
}>();
</script>

<template>
    <button :class="['the-button', color, size]" @click="$emit('click')">
        {{ text }}
    </button>
</template>

<style lang="scss">
.the-button {
    min-width: max-content;
    border-radius: 0.5em;
    color: $white;
    font-weight: 600;
    white-space: nowrap;
}

// color
.the-button.kiosk-primary {
    background-color: $kiosk-primary;
}

.the-button.admin-primary {
    background-color: $admin-primary;
}

.the-button.green {
    background-color: $green;
}

.the-button.red {
    background-color: $red;
}

.the-button.gray {
    background-color: $gray-dark;
}

// size
.the-button.sm {
    width: 2rem;
    padding: 0.3rem 0.5rem;
    font-size: 1rem;
}

.the-button.md {
    width: 4rem;
    padding: 0.5rem 0.8rem;
    font-size: 1.5rem;
}

.the-button.lg {
    width: 15rem;
    padding: 0.8rem 1rem;
    font-size: 2rem;
}
</style>
