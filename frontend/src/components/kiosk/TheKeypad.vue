<script setup lang="ts">
import TheKeypadButton from './TheKeypadButton.vue';
</script>

<template lang="">
    <div class="keypad">
        <TheKeypadButton
            buttonValue="1"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="2"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="3"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="4"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="5"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="6"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="7"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="8"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="9"
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue="."
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton
            buttonValue=""
            @input="(buttonValue) => $emit('input', buttonValue)" />
        <TheKeypadButton buttonValue="&larr;" @input="$emit('delete')" />
    </div>
</template>

<style lang="scss">
.keypad {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-auto-rows: minmax(5rem, auto);
    grid-gap: 1rem;
}

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
