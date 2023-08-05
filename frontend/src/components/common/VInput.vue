<script setup lang="ts">
const props = withDefaults(
    defineProps<{
        id: string;
        type?: string;
        accept?: string;
        multiple?: boolean;
        label?: string;
        name?: string;
        value?: string | number;
        readonly?: boolean;
        minlength?: number;
        maxlength?: number;
        min?: number;
        max?: number;
        textAlign?: 'left' | 'right' | 'center' | 'justify';
        color?: 'kiosk-primary' | 'admin-primary';
        size?: 'sm' | 'md' | 'lg';
        isError?: boolean;
        isFocus?: boolean;
    }>(),
    {
        type: 'text',
        value: '',
        multiple: false,
        readonly: false,
        size: 'sm',
        textAlign: 'left',
        isError: false,
        isFocus: false,
    }
);

const emit = defineEmits<{
    (e: 'input', value: string): void;
    (e: 'change'): void;
    (e: 'enter'): void;
    (e: 'focus'): void;
}>();

// Emit input event when it's not readonly
const handleInput = function handleAppInput(event: Event) {
    if (props.readonly) return;
    emit('input', (event.target as HTMLInputElement).value);
};
</script>

<template>
    <div
        :class="[
            'v-input',
            color,
            size,
            isError ? 'error' : '',
            isFocus ? 'focus' : '',
        ]">
        <label v-if="label" :for="id">{{ label }}</label>
        <input
            :id="id"
            :type="type"
            :accept="accept"
            :name="name"
            :value="value"
            :readonly="readonly"
            :minlength="minlength"
            :maxlength="maxlength"
            :min="min"
            :max="max"
            :style="{ textAlign }"
            :multiple="multiple"
            @input="handleInput"
            @change="$emit('change')"
            @keyup.enter="$emit('enter')"
            @focus="$emit('focus')" />
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

.v-input.kiosk-primary.focus {
    input {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.v-input.admin-primary {
    input:focus {
        outline: $admin-deep-primary 3px solid;
    }
}

.v-input.admin-primary.focus {
    input {
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
    font-size: 1.4rem;

    input {
        padding: 0.5rem;
        border-radius: 0.5em;
        font-size: 1.4rem;
    }
}

.v-input.lg {
    font-size: 1.7rem;

    input {
        padding: 1rem;
        border-radius: 0.5em;
        font-size: 1.7rem;
    }
}

//error
.v-input.error {
    outline: $red 3px solid;
}
</style>
