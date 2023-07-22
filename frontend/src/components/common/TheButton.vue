<script setup lang="ts">
const props = defineProps<{
    text: string;
    color: 'kiosk-primary' | 'admin-primary' | 'green' | 'red' | 'gray';
    size: 'sm' | 'md' | 'lg';
    emitMessage: string;
}>();
</script>

<template>
    <button
        :class="['the-button', props.color, props.size]"
        @click="$emit(emitMessage)">
        {{ text }}
    </button>
</template>

<style lang="scss" scoped>
.the-button {
    border-radius: 0.2rem;
    color: $white;
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
    width: 15rem;
    padding: 1rem;
}
</style>
