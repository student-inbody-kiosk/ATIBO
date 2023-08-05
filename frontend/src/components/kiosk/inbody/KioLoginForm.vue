<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import services from '@/apis/services';
import regexes from '@/constants/regexes';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import type { StudentSimple } from '@/types/students.interface';
import { toastCenterErrorMessage } from '@/utils/toastManager';

const props = defineProps<{
    student: StudentSimple;
}>();

const emit = defineEmits<{
    (e: 'update-student', value: null): void; // delete student
}>();

const router = useRouter();

const studentPw = ref<string>('');

// handle studentPw input
const handleInput = function inputStudentPw(value: string) {
    studentPw.value = value;
};

// student login
const handleSubmit = function loginStudent() {
    if (!regexes.studentPw.test(studentPw.value)) {
        toastCenterErrorMessage('비밀번호는 4자리 숫자로 입력해주세요');
        return;
    }

    const grade = props.student.grade;
    const room = props.student.room;
    const number = props.student.number;
    const password = studentPw.value;

    studentPw.value = '';

    services.loginStudent(grade, room, number, password).then(() => {
        router.push({
            name: 'kiosk-inbody-list',
            params: { grade, room, number },
        });
    });
};
</script>

<template>
    <div class="kiosk-login-form">
        <form class="kiosk-login-form__form" @submit.prevent="handleSubmit">
            <VInput
                id="kio-studentPw"
                type="password"
                :maxlength="4"
                label="비밀번호"
                textAlign="center"
                size="lg"
                color="kiosk-primary"
                :value="studentPw"
                @input="handleInput" />
            <VButton text="로그인" type="submit" color="green" size="md" />
            <VButton
                text="취소"
                color="gray"
                size="md"
                @click="$emit('update-student', null)" />
        </form>
        <TheKeypad :value="studentPw" @input="handleInput" />
    </div>
</template>

<style lang="scss">
.kiosk-login-form {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
}

.kiosk-login-form__form {
    display: flex;
    align-items: center;
    gap: 1rem;
}
</style>
