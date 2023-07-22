<script setup lang="ts">
const props = defineProps<{
    text: string;
    color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
    size: 'sm' | 'md' | 'lg';
}>();
</script>

<template>
    <button :class="['button', props.color, props.size]">{{ text }}</button>
</template>

<style lang="scss">
.button {
    border-radius: 0.2rem;
    color: $black;
}

.kiosk-primary {
    background-color: $kiosk-primary;
}

.admin-primary {
    background-color: $admin-primary;
}

.green {
    background-color: $green;
}

.red {
    background-color: $red;
}

.gray {
    background-color: $gray-dark;
}

.sm {
    width: 2rem;
}

.md {
    width: 4rem;
}

.lg {
    width: 6rem;
}
</style>
