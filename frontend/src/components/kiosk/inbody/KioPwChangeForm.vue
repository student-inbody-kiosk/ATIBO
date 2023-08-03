KioPwChangeModal
<script setup lang="ts">
import { ref, reactive } from 'vue';
import services from '@/apis/services';
import VButton from '@/components/common/VButton.vue';
import VInput from '@/components/common/VInput.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import regexes from '@/constants/regexes';
import { toastCenterErrorMessage } from '@/utils/toastManager';

const props = defineProps<{
    grade: number;
    room: number;
    number: number;
}>();

const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');

const currentFocusedInput = ref('kiosk-student-old-password'); // change focused input border color
const currentFocusedValue = reactive({ value: oldPassword }); // switch the value linked to the keypad

const handleFocus = function changeCurrentFocused() {
    const id = event?.target?.id as string;
    switch (id) {
        case 'kiosk-student-old-password':
            currentFocusedInput.value = 'kiosk-student-old-password';
            currentFocusedValue.value = oldPassword;
            break;
        case 'kiosk-student-new-password':
            currentFocusedInput.value = 'kiosk-student-new-password';
            currentFocusedValue.value = newPassword;
            break;
        case 'kiosk-student-confirm-password':
            currentFocusedInput.value = 'kiosk-student-confirm-password';
            currentFocusedValue.value = confirmPassword;
            break;
    }
};

const handleInput = function handleKeypadInput(value: string) {
    const id = currentFocusedInput;
    switch (id.value) {
        case 'kiosk-student-old-password':
            oldPassword.value = value;
            break;
        case 'kiosk-student-new-password':
            newPassword.value = value;
            break;
        case 'kiosk-student-confirm-password':
            confirmPassword.value = value;
            break;
    }
};

// handle input
const handleInputOldPassword = function (value: string) {
    oldPassword.value = value;
};
const handleInputNewPassword = function (value: string) {
    newPassword.value = value;
};
const handleInputConfirmPassword = function (value: string) {
    confirmPassword.value = value;
};

// change the student pw
const handleSubmit = function updateStudentPw() {
    if (!regexes.studentPw.test(oldPassword.value)) {
        toastCenterErrorMessage('기존 비밀번호는 4자리 숫자입니다');
        return;
    }
    if (!regexes.studentPw.test(newPassword.value)) {
        toastCenterErrorMessage('새 비밀번호를 4자리 숫자로 입력해주세요');
        return;
    }
    if (!(newPassword.value == confirmPassword.value)) {
        toastCenterErrorMessage('새 비밀번호가 일치하지 않습니다');
        return;
    }

    services.updateStudentPw(
        props.grade,
        props.room,
        props.number,
        oldPassword.value,
        newPassword.value,
        confirmPassword.value
    );

    oldPassword.value = '';
    newPassword.value = '';
    confirmPassword.value = '';
};
</script>

<template>
    <div class="kiosk-pw-change-form">
        <form class="kiosk-pw-change-form__form" @submit.prevent="handleSubmit">
            <VInput
                id="kiosk-student-old-password"
                label="현재 비밀번호"
                type="password"
                size="md"
                color="kiosk-primary"
                :isFocus="currentFocusedInput === 'kiosk-student-old-password'"
                :value="oldPassword"
                :maxlength="4"
                @input="handleInputOldPassword"
                @focus="handleFocus" />
            <VInput
                id="kiosk-student-new-password"
                label="새 비밀번호"
                type="password"
                size="md"
                color="kiosk-primary"
                :isFocus="currentFocusedInput === 'kiosk-student-new-password'"
                :value="newPassword"
                :maxlength="4"
                @input="handleInputNewPassword"
                @focus="handleFocus" />
            <VInput
                id="kiosk-student-confirm-password"
                label="비밀번호 확인"
                type="password"
                size="md"
                color="kiosk-primary"
                :isFocus="
                    currentFocusedInput === 'kiosk-student-confirm-password'
                "
                :value="confirmPassword"
                :maxlength="4"
                @input="handleInputConfirmPassword"
                @focus="handleFocus" />
            <VButton text="변경하기" type="submit" color="green" size="md" />
        </form>
        <TheKeypad :value="currentFocusedValue.value" @input="handleInput" />
    </div>
</template>

<style lang="scss">
.kiosk-pw-change-form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
}

.kiosk-pw-change-form__form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    gap: 1rem;
}
</style>
