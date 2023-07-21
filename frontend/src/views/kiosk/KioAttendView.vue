<script setup lang="ts">
import KioHeader from '@/components/kiosk/KioHeader.vue';
import KioLayout from '@/components/kiosk/KioLayout.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';

import { ref } from 'vue';
let attendData = ref('');

const handleInput = function updateAttendData(value: string) {
    attendData.value += value;
};

const deleteInput = function deleteAttendData() {
    if (attendData.value.length == 0) return;
    attendData.value = attendData.value.slice(0, -1);
};
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <KioHeader title="Title" backUrl="kiosk" />
        </template>
        <template #kiosk-main>
            <div>{{ attendData }}</div>
            <TheKeypad @input="handleInput" @delete="deleteInput" />
        </template>
    </KioLayout>
</template>

<style lang="scss"></style>
