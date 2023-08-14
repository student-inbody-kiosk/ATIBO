<script setup lang="ts">
import LoadingSvg from '@/assets/loading.svg';

withDefaults(
    defineProps<{
        color: 'kiosk-primary' | 'admin-primary';
        size: 'sm' | 'md' | 'lg';
    }>(),
    {
        size: 'md',
    }
);

defineEmits<{
    (e: 'click'): void;
}>();
</script>

<template>
    <div :class="['v-loading', color, size]">
        <LoadingSvg />
    </div>
</template>

<style lang="scss">
.v-loading {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

// color
.v-loading.kiosk-primary {
    path {
        fill: $kiosk-primary;
    }
}

.v-loading.admin-primary {
    path {
        fill: $admin-primary;
    }
}

// size
.v-loading.sm {
    svg {
        width: 3rem;
        height: 3rem;
    }
}

.v-loading.md {
    svg {
        width: 7rem;
        height: 7rem;
    }
}

.v-loading.lg {
    svg {
        width: 10rem;
        height: 10rem;
    }
}
</style>
