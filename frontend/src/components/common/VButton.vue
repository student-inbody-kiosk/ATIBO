<script setup lang="ts">
withDefaults(
    defineProps<{
        text: string;
        color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
        size: 'sm' | 'md' | 'lg';
    }>(),
    {
        size: 'sm',
    }
);

defineEmits<{
    (e: 'click'): void;
}>();
</script>

<template>
    <button :class="['v-button', color, size]" @click="$emit('click')">
        {{ text }}
    </button>
</template>

<style lang="scss">
.v-button {
    min-width: max-content;
    border-radius: 0.5em;
    color: $white;
    font-weight: 600;
    white-space: nowrap;
}

// color
.v-button.kiosk-primary {
    background-color: $kiosk-primary;
}

.v-button.admin-primary {
    background-color: $admin-primary;
}

.v-button.green {
    background-color: $green;
}

.v-button.red {
    background-color: $red;
}

.v-button.gray {
    background-color: $gray-dark;
}

// size
.v-button.sm {
    width: 2rem;
    padding: 0.3rem 0.5rem;
    font-size: 1rem;
}

.v-button.md {
    width: 4rem;
    padding: 0.5rem 0.8rem;
    font-size: 1.5rem;
}

.v-button.lg {
    width: 15rem;
    padding: 0.8rem 1rem;
    font-size: 2rem;
}
</style>
