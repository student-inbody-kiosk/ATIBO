<script setup lang="ts">
import type { Ref } from 'vue';

const props = withDefaults(
    defineProps<{
        id: string;
        type?: string;
        label?: string;
        value?: string | number;
        readonly?: boolean;
        minlength?: number;
        maxlength?: number;
        min?: number;
        max?: number;
        textAlign?: 'left' | 'right' | 'center' | 'justify';
        isError?: boolean;
        color?: 'kiosk-primary' | 'admin-primary';
        size?: 'sm' | 'md' | 'lg';
        inputRef?: Ref;
    }>(),
    {
        type: 'text',
        value: '',
        readonly: false,
        textAlign: 'left',
        isError: false,
        size: 'sm',
    }
);

const emit = defineEmits<{
    (e: 'input', value: string): void;
    (e: 'enter', value: string): void;
}>();

// Emit input event when it's not readonly
const handleInput = function handleAppInput(event: Event) {
    if (props.readonly) return;
    emit('input', (event.target as HTMLInputElement).value);
};
</script>

<template>
    <div :class="['v-input', color, size, isError ? 'error' : '']">
        <label :for="id">{{ label }}</label>
        <input
            :id="id"
            :ref="inputRef"
            :type="type"
            :value="value"
            :readonly="readonly"
            :minlength="minlength"
            :maxlength="maxlength"
            :min="min"
            :max="max"
            :style="{ textAlign }"
            @input="handleInput"
            @keyup.enter="$emit('enter')" />
    </div>
</template>

<style lang="scss">
.v-input {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.v-input label {
    font-weight: 600;
}

//color
.v-input.kiosk-primary {
    input:focus {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.v-input.admin-primary {
    input:focus {
        outline: $admin-deep-primary 3px solid;
    }
}

//size
.v-input.sm {
    font-size: 1rem;

    input {
        padding: 0.2rem;
        font-size: 1rem;
    }
}

.v-input.md {
    font-size: 1.2rem;

    input {
        padding: 0.5rem;
        border-radius: 0.5em;
        font-size: 1.2rem;
    }
}

.v-input.lg {
    font-size: 2rem;

    input {
        padding: 1rem;
        border-radius: 0.5em;
        font-size: 2rem;
    }
}

//error
.v-input.error {
    outline: $red 3px solid;
}
</style>
