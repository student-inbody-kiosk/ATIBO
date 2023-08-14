<script setup lang="ts">
import { ref } from 'vue';
import services from '@/apis/services';
import { regexes } from '@/constants/regexes';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import { toastCenterErrorMessage } from '@/utils/toastManager';
import type { StudentSimple } from '@/types/students.interface';
import { useAxios } from '@/hooks/useAxios';
import VLoading from '@/components/common/VLoading.vue';

const emit = defineEmits<{
    (e: 'update-student', value: StudentSimple): void;
}>();

const studentNum = ref<string>('');

// Handle studentNum input
const handleInput = function inputStudentNum(value: string) {
    studentNum.value = value;
};

/* Check studnet with studentNum asynchronously*/
const { fetchData: checkStudent, isLoading } = useAxios<StudentSimple>(
    null,
    services.checkStudent
);

const handleSubmit = function onSubmitCheckStudent() {
    if (!regexes.studentNum.reg.test(studentNum.value)) {
        toastCenterErrorMessage(regexes.studentNum.condition);
        return;
    }
    const grade = parseInt(studentNum.value.slice(0, 1));
    const room = parseInt(studentNum.value.slice(1, 3));
    const number = parseInt(studentNum.value.slice(3));

    studentNum.value = '';

    checkStudent(grade, room, number).then((res) => {
        emit('update-student', res);
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="kiosk-primary" class="kiosk-loading" />
    <div class="kiosk-student-form">
        <form class="kiosk-student-form__form" @submit.prevent="handleSubmit">
            <VInput
                id="kio-student-num"
                type="text"
                label="학번"
                :value="studentNum"
                :maxlength="6"
                textAlign="center"
                size="xl"
                color="kiosk-primary"
                @input="handleInput" />
            <VButton text="검색하기" type="submit" color="green" size="lg" />
        </form>
        <TheKeypad :value="studentNum" @input="handleInput" />
    </div>
</template>

<style lang="scss">
.kiosk-student-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4rem;
}

.kiosk-student-form__form {
    display: flex;
    align-items: center;
    gap: 1rem;
}
</style>
