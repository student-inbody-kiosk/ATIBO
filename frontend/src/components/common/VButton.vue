<script setup lang="ts">
import throttle from '@/utils/throttle';

withDefaults(
    defineProps<{
        text: string;
        color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
        size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
        type?: 'button' | 'submit' | 'reset';
    }>(),
    {
        size: 'xs',
        type: 'button',
    }
);

const emit = defineEmits<{
    (e: 'click'): void;
}>();

// throttle the click event
const handleClick = function () {
    throttle(emit('click'), 500);
};
</script>

<template>
    <button
        :class="['v-button', color, size]"
        :type="type"
        @click="handleClick">
        {{ text }}
    </button>
</template>

<style lang="scss">
.v-button {
    min-width: max-content;
    border-radius: 0.5em;
    color: $white;
    font-weight: 700;
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
.v-button.xs {
    min-width: 2rem;
    padding: 0.3rem 0.6rem;
    font-size: 1rem;
}

.v-button.sm {
    min-width: 3rem;
    padding: 0.4rem 0.7rem;
    font-size: 1.2rem;
}

.v-button.md {
    min-width: 4rem;
    padding: 0.5rem 0.8rem;
    font-size: 1.4rem;
}

.v-button.lg {
    min-width: 6rem;
    padding: 0.7rem 1rem;
    font-size: 1.6rem;
}

.v-button.xl {
    min-width: 18rem;
    padding: 1.1rem 1.4rem;
    font-size: 2rem;
}
</style>
