<script setup lang="ts">
import type { Ref } from 'vue';

const props = withDefaults(
    defineProps<{
        text?: string;
        size?: 'sm' | 'md' | 'lg';
    }>(),
    {
        text: '서버로부터 정보를 불러오지 못했습니다',
        size: 'sm',
    }
);
</script>

<template>
    <div :class="['v-error', size]">
        {{ text }}
    </div>
</template>

<style lang="scss">
.v-error {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    color: transparentize($black, 0.7);
    font-weight: 600;
    text-align: center;
}

.v-error.sm {
    font-size: 1.2rem;
}

.v-error.md {
    font-size: 1.3rem;
}

.v-error.lg {
    font-size: 1.7rem;
}
</style>
