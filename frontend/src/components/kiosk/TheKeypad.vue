<script setup lang="ts">
import TheKeypadButton from '@/components/kiosk/TheKeypadButton.vue';

const props = defineProps<{
    value: string;
}>();

const emit = defineEmits<{
    (e: 'input', value: string): void;
}>();

const handleClick = function handleKeypadClick(value: string) {
    let newValue = '';
    switch (value) {
        case 'cancel':
            break;
        case 'delete':
            newValue = props.value.slice(0, -1);
            break;
        default:
            newValue = props.value + value;
    }
    emit('input', newValue);
};
</script>

<template lang="">
    <div class="keypad">
        <TheKeypadButton text="1" @click="handleClick('1')" />
        <TheKeypadButton text="2" @click="handleClick('2')" />
        <TheKeypadButton text="3" @click="handleClick('3')" />
        <TheKeypadButton text="4" @click="handleClick('4')" />
        <TheKeypadButton text="5" @click="handleClick('5')" />
        <TheKeypadButton text="6" @click="handleClick('6')" />
        <TheKeypadButton text="7" @click="handleClick('7')" />
        <TheKeypadButton text="8" @click="handleClick('8')" />
        <TheKeypadButton text="9" @click="handleClick('9')" />
        <TheKeypadButton text="취소" @click="handleClick('cancel')" />
        <TheKeypadButton text="0" @click="handleClick('0')" />
        <TheKeypadButton text="&larr;" @click="handleClick('delete')" />
    </div>
</template>

<style lang="scss">
.keypad {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-auto-rows: minmax(6vh, 9vh);
    grid-gap: 1rem;
    width: 100%;
}
</style>
