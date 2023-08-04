<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
defineProps<{
    isGraph: boolean;
}>();

defineEmits<{
    (e: 'update-is-graph', value: boolean): void; // delete student
}>();
</script>

<template>
    <div class="kiosk-inbody-buttons">
        <VButton
            text="표"
            :color="isGraph ? 'gray' : 'kiosk-primary'"
            size="md"
            @click="$emit('update-is-graph', false)" />
        <VButton
            text="그래프"
            :color="!isGraph ? 'gray' : 'kiosk-primary'"
            size="md"
            @click="$emit('update-is-graph', true)" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-buttons {
    display: flex;

    gap: 1rem;
}
</style>
