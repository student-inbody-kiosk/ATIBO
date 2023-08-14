<script setup lang="ts">
import throttle from '@/utils/throttle';

withDefaults(
    defineProps<{
        text: string;
        color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
        size?: 'sm' | 'md' | 'lg' | 'xl';
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
    min-width: 2rem;
    padding: 0.4rem 0.7rem;
    font-size: 1rem;
}

.v-button.md {
    min-width: 4rem;
    padding: 0.5rem 0.8rem;
    font-size: 1.2rem;
}

.v-button.lg {
    min-width: 10vw;
    padding: 1.2vh 1vh;
    font-size: 2.8vh;
}

.v-button.xl {
    min-width: 30vh;
    padding: 2vh 2vw;
    font-size: 3.5vh;
}
</style>
