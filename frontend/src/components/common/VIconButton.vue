<script setup lang="ts">
const props = withDefaults(
    defineProps<{
        text?: string;
        size?: 'sm' | 'md' | 'lg';
    }>(),
    {
        size: 'md',
    }
);

const emit = defineEmits<{
    (e: 'click'): void;
}>();
</script>

<template>
    <button :class="['app-icon', size]" @click="$emit('click')">
        <slot />
        <span>{{ text }}</span>
    </button>
</template>

<style lang="scss">
.app-icon {
    display: flex;
    flex-direction: column;
    align-items: center;
}

// size
.app-icon.sm {
    font-size: 1rem;
}

.app-icon.md {
    font-size: 1.2rem;
}

.app-icon.lg {
    font-size: 1.4rem;
}
</style>
