<script setup lang="ts">
import { ref } from 'vue';

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
}>();

// Method for focusing on input element
const inputRef = ref<HTMLInputElement | null>(null);
const inputFocus = function appInputFocus() {
    inputRef.value?.focus();
};

// Emit input event when it's not readonly
const handleInput = function handleAppInput(event: Event) {
    if (props.readonly) return;
    emit('input', (event.target as HTMLInputElement).value);
};

defineExpose({
    inputFocus,
});
</script>

<template>
    <div :class="['the-input', color, size, isError ? 'error' : '']">
        <label :for="id">{{ label }}</label>
        <input
            :id="id"
            ref="inputRef"
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
.the-input {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.the-input label {
    font-weight: 600;
}

//color
.the-input.kiosk-primary {
    input:focus {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.the-input.admin-primary {
    input:focus {
        outline: $admin-deep-primary 3px solid;
    }
}

//size
.the-input.sm {
    font-size: 1rem;

    input {
        padding: 0.3rem;
        font-size: 1rem;
    }
}

.the-input.md {
    font-size: 1.2rem;
    padding: 0.5rem;
}

.the-input.lg {
    font-size: 1.2rem;
    padding: 0.5rem;
}

//error
.the-input.error {
    outline: $red 3px solid;
}
</style>
