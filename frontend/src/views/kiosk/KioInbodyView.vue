<script setup lang="ts">
import TheKeypad from '@/components/kiosk/TheKeypad.vue';

import { onBeforeMount, ref } from 'vue';

const emit = defineEmits(['before-mount']);
let attendData = ref('');

onBeforeMount(() => {
    emit('before-mount', { title: '인바디', routeName: 'kiosk' });
});

const handleInput = function updateAttendData(value: string) {
    attendData.value += value;
};

const deleteInput = function deleteAttendData() {
    if (attendData.value.length == 0) return;
    attendData.value = attendData.value.slice(0, -1);
};
</script>

<template>
    <div>
        <div>{{ attendData }}</div>
        <TheKeypad @input="handleInput" @delete="deleteInput" />
    </div>
</template>

<style lang="scss"></style>
