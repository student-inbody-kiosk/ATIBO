<script setup lang="ts">
import AppIcon from '@/components/common/IconButton.vue';

const emit = defineEmits<{
    (e: 'close-modal'): void;
}>();
</script>

<template>
    <teleport to="#modal">
        <div class="app-modal" @click="$emit('close-modal')">
            <div class="app-modal__content" @click.stop>
                <div class="app-modal__button">
                    <AppIcon @click="$emit('close-modal')">
                        <font-awesome-icon icon="fa-xmark" size="2xl" />
                    </AppIcon>
                </div>
                <Transition name="app-modal">
                    <slot />
                </Transition>
            </div>
        </div>
    </teleport>
</template>

<style lang="scss">
.app-modal {
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

.app-modal__content {
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

.app-modal__button {
    position: absolute;
    top: 0.5rem;
    right: 0.5rem;
}
</style>
