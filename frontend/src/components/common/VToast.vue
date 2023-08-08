<script setup lang="ts">
import CheckSvg from '@/assets/check.svg';
import XmarkSvg from '@/assets/xmark.svg';
import ExclamationSvg from '@/assets/exclamation.svg';

withDefaults(
    defineProps<{
        type?: 'success' | 'error' | 'info';
        message: string;
        position?: 'top' | 'center';
        size?: 'sm' | 'md' | 'lg';
    }>(),
    {
        type: 'info',
        position: 'top',
        size: 'sm',
    }
);
</script>

<template>
    <div :class="['v-toast', size, position, type]" @click.stop>
        <div class="v-toast__content" @click="$emit('close')">
            <CheckSvg v-if="type === 'success'" />
            <XmarkSvg v-else-if="type === 'error'" />
            <ExclamationSvg v-else />
            <p>
                {{ message }}
            </p>
        </div>
    </div>
</template>

<style lang="scss">
.v-toast {
    @include z-index(toast);
    font-weight: 600;
    animation: toast 0.5s ease-in-out;
}

@keyframes toast {
    from {
        opacity: 0;
        transform: translateY(-100px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.v-toast__content {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.5em;
    border-radius: 1em;
    background-color: $white;
    box-shadow: 0px 3px 5px 5px transparentize($black, 0.9);
    text-align: center;
}

// position
.v-toast.top {
    display: flex;
    justify-content: center;
}

.v-toast.center {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;

    .v-toast__content {
        max-width: 90vw;
        max-height: 90vh;
    }
}

// color
.v-toast.success path {
    color: $green;
}

.v-toast.error path {
    color: $red;
}

.v-toast.info path {
    color: $yellow;
}

// size
.v-toast.sm {
    font-size: 1rem;

    svg {
        width: 1.5rem;
        height: 1.5rem;
    }

    .v-toast__content {
        padding: 1rem;
    }
}

.v-toast.md {
    font-size: 1.5rem;

    svg {
        width: 2rem;
        height: 2rem;
    }

    .v-toast__content {
        padding: 1.5rem;
    }
}

.v-toast.lg {
    font-size: 2rem;

    svg {
        width: 2.5rem;
        height: 2.5rem;
    }

    .v-toast__content {
        padding: 4rem 2rem;
    }
}
</style>
