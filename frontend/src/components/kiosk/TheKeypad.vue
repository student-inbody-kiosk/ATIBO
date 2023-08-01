<script setup lang="ts">
import TheKeypadButton from '@/components/kiosk/TheKeypadButton.vue';

defineEmits<{
    (e: 'click', value: string): void;
}>();
</script>

<template lang="">
    <div class="keypad">
        <TheKeypadButton text="1" @click="$emit('click', '1')" />
        <TheKeypadButton text="2" @click="$emit('click', '2')" />
        <TheKeypadButton text="3" @click="$emit('click', '3')" />
        <TheKeypadButton text="4" @click="$emit('click', '4')" />
        <TheKeypadButton text="5" @click="$emit('click', '5')" />
        <TheKeypadButton text="6" @click="$emit('click', '6')" />
        <TheKeypadButton text="7" @click="$emit('click', '7')" />
        <TheKeypadButton text="8" @click="$emit('click', '8')" />
        <TheKeypadButton text="9" @click="$emit('click', '9')" />
        <TheKeypadButton text="cancel" @click="$emit('click', 'cancel')" />
        <TheKeypadButton text="0" @click="$emit('click', '0')" />
        <TheKeypadButton text="&larr;" @click="$emit('click', 'delete')" />
    </div>
</template>

<style lang="scss">
.keypad {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-auto-rows: minmax(8vh, 10vh);
    grid-gap: 1rem;
    width: 100%;
}
</style>
