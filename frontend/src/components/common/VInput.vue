<script setup lang="ts">
const props = withDefaults(
    defineProps<{
        id: string;
        type?: string;
        accept?: string;
        multiple?: boolean;
        label?: string;
        name?: string;
        ariaLabel?: string;
        value?: string | number;
        readonly?: boolean;
        minlength?: number;
        maxlength?: number;
        min?: number | string;
        max?: number | string;
        textAlign?: 'left' | 'right' | 'center' | 'justify';
        color?: 'kiosk-primary' | 'admin-primary';
        size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
        isError?: boolean;
        condition?: string;
        isFocus?: boolean;
        placeholder?: string;
    }>(),
    {
        type: 'text',
        value: '',
        ariaLabel: '',
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
    (e: 'input', value: string, target: HTMLInputElement): void;
    (e: 'change', target: HTMLInputElement): void;
    (e: 'enter'): void;
    (e: 'focus', target: HTMLInputElement): void;
}>();

// Emit input event when it's not readonly
const handleInput = function handleAppInput(event: Event) {
    if (props.readonly) return;
    emit(
        'input',
        (event.target as HTMLInputElement).value,
        event.target as HTMLInputElement
    );
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
                :aria-label="ariaLabel"
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
                :aria-label="ariaLabel"
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
    input:focus,
    textarea:focus {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.v-input.kiosk-primary.focus {
    input,
    textarea {
        outline: $kiosk-deep-primary 3px solid;
    }
}

.v-input.admin-primary {
    input:focus,
    textarea:focus {
        outline: $admin-deep-primary 3px solid;
    }
}

.v-input.admin-primary.focus {
    input,
    textarea {
        outline: $admin-deep-primary 3px solid;
    }
}

//size
.v-input.xs {
    font-size: 1rem;

    input,
    textarea {
        font-size: 1rem;
        width: 100%;
        text-align: center;
    }
}

.v-input.sm {
    font-size: 1rem;

    input,
    textarea {
        padding: 0.3rem;
        font-size: 1rem;
        width: 4rem;
        border-radius: 0.5em;
    }

    input[type='month'] {
        width: 7.5rem;
    }

    input[type='date'] {
        width: 10rem;
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
    font-size: 3vh;

    input,
    textarea {
        padding: 1.5vh;
        max-width: 50vw;
        border-radius: 0.5em;
        font-size: 3vh;
    }
}

//error
.v-input.error {
    .v-input__condition {
        color: $red;
    }
}
</style>
