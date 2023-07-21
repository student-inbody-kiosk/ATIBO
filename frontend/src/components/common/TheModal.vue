<script setup lang="ts">
import IconButton from '@/components/common/IconButton.vue';
</script>

<template>
    <div class="modal">
        <div class="modal__content">
            <IconButton
                class="modal__icon"
                emitMessage="close"
                text="닫기"
                @close="$emit('close-modal')">
                <template #icon> </template>
            </IconButton>
            <slot name="modal-content" />
        </div>
    </div>
</template>

<style lang="scss">
.modal {
    @include z-index(modal);
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: $gray-opacity;
}

.modal__content {
    width: 80%;
    height: 80%;
    background-color: $white;
}
</style>
