<script setup lang="ts">
withDefaults(
    defineProps<{
        text?: string;
        size?: 'sm' | 'md' | 'lg';
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
    <button :class="['v-icon-button', size]" @click="$emit('click')">
        <slot />
        <span>{{ text }}</span>
    </button>
</template>

<style lang="scss">
.v-icon-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-weight: 600;
}

// size
.v-icon-button.sm {
    font-size: 0.9rem;
}

.v-icon-button.md {
    font-size: 1rem;
}

.v-icon-button.lg {
    font-size: 1.2rem;
}
</style>
