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
        size?: 'sm' | 'md' | 'lg' | 'xl';
        isError?: boolean;
        condition?: string;
        isFocus?: boolean;
        placeholder?: string;
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
        placeholder: '',
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
        <div class="v-input__label-input">
            <label v-if="label" :for="id">{{ label }}</label>
            <textarea
                v-if="type === 'textarea'"
                :id="id"
                :name="name"
                :value="value"
                :readonly="readonly"
                :minlength="minlength"
                :maxlength="maxlength"
                :min="min"
                :max="max"
                :style="{ textAlign }"
                :multiple="multiple"
                :placeholder="placeholder"
                @input="handleInput"
                @change="$emit('change')"
                @keyup.enter="$emit('enter')"
                @focus="$emit('focus')" />
            <input
                v-else
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
                :placeholder="placeholder"
                @input="handleInput"
                @change="$emit('change')"
                @keyup.enter="$emit('enter')"
                @focus="$emit('focus')" />
        </div>
        <p class="v-input__condition" v-if="condition">{{ condition }}</p>
    </div>
</template>

<style lang="scss">
.v-input {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    gap: 0.2rem;
}

.v-input__label-input {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.v-input__label-input label {
    font-weight: 600;
    white-space: nowrap;
}

.v-input__label-input textarea {
    width: 100%;
    min-width: 17rem;
    min-height: 5rem;
}

.v-input__condition {
    color: transparentize($black, 0.7);
    font-size: 0.9rem;
}

//color
.v-input.kiosk-primary {
    input,
    textarea:focus {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.v-input.kiosk-primary.focus {
    input,
    textarea:focus {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.v-input.admin-primary {
    input,
    textarea:focus {
        outline: $admin-deep-primary 3px solid;
    }
}

.v-input.admin-primary.focus {
    input,
    textarea:focus {
        outline: $admin-deep-primary 3px solid;
    }
}

//size
.v-input.sm {
    font-size: 1rem;

    input,
    textarea {
        padding: 0.2rem;
        font-size: 1rem;
    }
}

.v-input.md {
    font-size: 1.2rem;

    input,
    textarea {
        padding: 0.3rem;
        border-radius: 0.5em;
        font-size: 1.2rem;
    }
}

.v-input.lg {
    font-size: 1.4rem;

    input,
    textarea {
        padding: 0.5rem;
        border-radius: 0.5em;
        font-size: 1.4rem;
    }
}

.v-input.xl {
    font-size: 2rem;

    input,
    textarea {
        padding: 1rem;
        border-radius: 0.5em;
        font-size: 1.8rem;
    }
}

//error
.v-input.error {
    .v-input__condition {
        color: $red;
    }
}
</style>
