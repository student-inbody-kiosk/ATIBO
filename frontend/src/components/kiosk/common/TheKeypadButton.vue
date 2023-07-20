<script setup lang="ts">
defineProps<{
    buttonValue: string;
}>();
</script>

<template lang="">
    <button class="keypad__button" @click="$emit('input', buttonValue)">
        {{ buttonValue }}
    </button>
</template>

<style lang="scss">
.keypad__button {
    display: flex;
    align-items: center;
    justify-content: center;
    border: 0.2rem solid $kiosk-primary;
    border-radius: 1rem;
    text-align: center;
    color: $kiosk-primary;
}
</style>
