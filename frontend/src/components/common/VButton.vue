<script setup lang="ts">
import throttle from '@/utils/throttle';

withDefaults(
    defineProps<{
        text: string;
        color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
        size?: 'sm' | 'md' | 'lg';
        type?: 'button' | 'submit' | 'reset';
    }>(),
    {
        size: 'sm',
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
