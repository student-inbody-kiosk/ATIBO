<script setup lang="ts">
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
import InputGuide from '@/components/kiosk/InputGuide.vue';
import TheInput from '@/components/common/TheInput.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';

import { ref } from 'vue';
let studentInfo = ref('');

const handleInput = function addStudentInfo(value: string) {
    studentInfo.value += value;
};

const deleteInput = function deleteStudentInfo() {
    if (studentInfo.value.length == 0) return;
    studentInfo.value = studentInfo.value.slice(0, -1);
};

const handleChange = function updateStudentInfo(info: string) {
    studentInfo.value = info;
};

const handleSubmit = function checkAttendance() {
    console.log(studentInfo.value);
};
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <KioHeader title="출석 확인" backUrl="kiosk" />
        </template>
        <template #kiosk-main>
            <InputGuide />
            <TheInput
                type="text"
                refer="학년반번호"
                :value="studentInfo"
                @update-input="handleChange"
                @submit="handleSubmit" />
            <button @click="handleSubmit">완료</button>
            <TheKeypad @input="handleInput" @delete="deleteInput" />
        </template>
    </KioLayout>
</template>

<style lang="scss"></style>
