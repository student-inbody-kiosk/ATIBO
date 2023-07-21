<script setup lang="ts">
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
import InputGuide from '@/components/kiosk/InputGuide.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';

import { ref } from 'vue';
let studentInfo = ref('');

const handleInput = function updateAttendData(value: string) {
    studentInfo.value += value;
};

const deleteInput = function deleteAttendData() {
    if (studentInfo.value.length == 0) return;
    studentInfo.value = studentInfo.value.slice(0, -1);
};
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <KioHeader title="출석 확인" backUrl="kiosk" />
        </template>
        <template #kiosk-main>
            <InputGuide />
            <div>{{ studentInfo }}</div>
            <TheKeypad @input="handleInput" @delete="deleteInput" />
        </template>
    </KioLayout>
</template>

<style lang="scss"></style>
