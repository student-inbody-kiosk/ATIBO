<script setup lang="ts">
import { ref } from 'vue';
import services from '@/apis/services';
import regexes from '@/constants/regexes';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import { toastCenterErrorMessage } from '@/utils/toastManager';
import type { StudentSimple } from '@/types/students.interface';

const emit = defineEmits<{
    (e: 'update-student', value: StudentSimple): void;
}>();

const studentNum = ref<string>('');

// handle studentNum input
const handleInput = function inputStudentNum(value: string) {
    studentNum.value = value;
};

// search the student based on studentNum
const handleSubmit = function checkStudent() {
    if (!regexes.studentNum.test(studentNum.value)) {
        toastCenterErrorMessage('학번 형식을 다시 확인해주세요');
        return;
    }
    const grade = parseInt(studentNum.value.slice(0, 1));
    const room = parseInt(studentNum.value.slice(1, 3));
    const number = parseInt(studentNum.value.slice(3));

    studentNum.value = '';

    services.checkStudent(grade, room, number).then((res) => {
        const id = res.id;
        const name = res.name;
        emit('update-student', { id, name, grade, room, number });
    });
};
</script>

<template>
    <div class="kiosk-student-form">
        <form class="kiosk-student-form__form" @submit.prevent="handleSubmit">
            <VInput
                id="kio-student-num"
                type="text"
                label="학번"
                :maxlength="6"
                textAlign="center"
                size="lg"
                color="kiosk-primary"
                :value="studentNum"
                @input="handleInput" />
            <VButton text="검색하기" type="submit" color="green" size="md" />
        </form>
        <TheKeypad :value="studentNum" @input="handleInput" />
    </div>
</template>

<style lang="scss">
.kiosk-student-form {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
}

.kiosk-student-form__form {
    display: flex;
    align-items: center;
    gap: 1rem;
}
</style>
