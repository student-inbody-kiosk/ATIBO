<script setup lang="ts">
withDefaults(
    defineProps<{
        text?: string;
        size?: 'sm' | 'md' | 'lg';
        type?: 'button' | 'submit' | 'reset';
        ariaLabel?: string;
    }>(),
    {
        size: 'sm',
        type: 'button',
        ariaLabel: '',
    }
);

defineEmits<{
    (e: 'click'): void;
}>();
</script>

<template>
    <button
        :id="`v-icon-button-${text}`"
        :class="['v-icon-button', size]"
        :type="type"
        :aria-label="ariaLabel"
        @click="$emit('click')">
        <slot />
        <label v-if="text" :for="`v-icon-button-${text}`">{{ text }}</label>
    </button>
</template>

<style lang="scss">
.v-icon-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.2rem;
    font-weight: 700;
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
