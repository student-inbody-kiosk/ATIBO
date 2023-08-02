<script setup lang="ts">
import { ref, inject } from 'vue';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import regexes from '@/constants/regexes';
import services from '@/apis/services';
import type { StudentSimple } from '@/types/students.interface';

const emit = defineEmits<{
    (e: 'student-info', value: StudentSimple): void;
}>();

const toast = inject('toast');
const studentNum = ref<string>('');

const handleInput = function handleStudentNumInput(value: string) {
    studentNum.value = value;
};

const handleClick = function handleKeypadClick(value: string) {
    switch (value) {
        case 'cancel':
            studentNum.value = '';
            break;
        case 'delete':
            studentNum.value = studentNum.value.slice(0, -1);
            break;
        default:
            studentNum.value += value;
    }
};

const handleSubmit = function handleCheckStudent() {
    if (!regexes.studentNum.test(studentNum.value)) {
        toast('학번 형식을 다시 확인해주세요', 'error', 2500, 'center', 'lg');
        return;
    }
    const grade = parseInt(studentNum.value.slice(0, 1));
    const room = parseInt(studentNum.value.slice(1, 3));
    const number = parseInt(studentNum.value.slice(3));

    studentNum.value = '';

    services
        .checkStudent(grade, room, number)
        .then((res) => {
            const id = res.id;
            const name = res.name;
            emit('student-info', { id, grade, room, number, name });
        })
        .catch((err) => {
            const message = err.detail ? err.detail : '출석에 실패했습니다';
            toast(message, 'error', 2500, 'center', 'lg');
        });
};
</script>

<template>
    <div class="kiosk-check-student">
        <form class="kiosk-check-student__form" @submit.prevent="handleSubmit">
            <VInput
                id="kio-search-student"
                type="text"
                label="학번"
                textAlign="center"
                size="lg"
                color="kiosk-primary"
                :value="studentNum"
                @input="handleInput" />
            <VButton text="검색하기" type="submit" color="green" size="md" />
        </form>
        <TheKeypad @click="handleClick" />
    </div>
</template>

<style lang="scss">
.kiosk-check-student {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    gap: 3rem;
}

.kiosk-check-student__form {
    display: flex;
    align-items: center;
    gap: 1rem;
}
</style>
