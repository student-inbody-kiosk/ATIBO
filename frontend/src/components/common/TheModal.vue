<script setup lang="ts">
import VIconButton from '@/components/common/VIconButton.vue';

withDefaults(
    defineProps<{
        color?: 'kiosk-secondary' | 'admin-secondary' | 'white';
    }>(),
    {
        color: 'white',
    }
);

defineEmits<{
    (e: 'close-modal'): void;
}>();
</script>

<template>
    <teleport to="#modal">
        <div class="the-modal" @click.stop="$emit('close-modal')">
            <div :class="['the-modal__content', color]" @click.stop>
                <div class="the-modal__button">
                    <VIconButton @click="$emit('close-modal')">
                        <font-awesome-icon icon="xmark" size="2x" />
                    </VIconButton>
                </div>
                <slot />
            </div>
        </div>
    </teleport>
</template>

<style lang="scss">
.the-modal {
    @include z-index(modal);
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.463);
}

.the-modal__content {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    position: relative;
    max-width: 80vh;
    max-height: 80vh;
    padding: 2.5rem 3rem 2rem;
    background-color: $white;
    border-radius: 1em;
}

.the-modal__button {
    position: absolute;
    top: 0.5rem;
    right: 0.5rem;
}

// color
.the-modal__content.kiosk-secondary {
    background-color: $kiosk-secondary;
}

.the-modal__content.admin-secondary {
    background-color: $admin-secondary;
}

.the-modal__content.white {
    background-color: $white;
}
</style>
