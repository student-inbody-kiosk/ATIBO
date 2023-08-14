KioPwChangeModal
<script setup lang="ts">
import { ref, reactive } from 'vue';
import services from '@/apis/services';
import VButton from '@/components/common/VButton.vue';
import VInput from '@/components/common/VInput.vue';
import VLoading from '@/components/common/VLoading.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import { studentRegexes } from '@/constants/regexes';
import { useAxios } from '@/hooks/useAxios';
import { toastCenterErrorMessage } from '@/utils/toastManager';

const props = defineProps<{
    grade: number;
    room: number;
    number: number;
}>();

/* Student change password logic with keypad */
const oldPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');

const OLD_PASSWORD_INPUT_ID = 'kiosk-student-old-password';
const NEW_PASSWORD_INPUT_ID = 'kiosk-student-new-password';
const CONFIRM_PASSWORD_INPUT_ID = 'kiosk-student-confirm-password';

// Focued input -> hand over to keypad
const currentFocusedInput = ref(OLD_PASSWORD_INPUT_ID); // input border color
const currentFocusedValue = reactive({ value: oldPassword }); // the value linked to the keypad

const handleFocus = function changeCurrentFocused() {
    const id = event?.target?.id as string;
    switch (id) {
        case OLD_PASSWORD_INPUT_ID:
            currentFocusedInput.value = OLD_PASSWORD_INPUT_ID;
            currentFocusedValue.value = oldPassword;
            break;
        case NEW_PASSWORD_INPUT_ID:
            currentFocusedInput.value = NEW_PASSWORD_INPUT_ID;
            currentFocusedValue.value = newPassword;
            break;
        case CONFIRM_PASSWORD_INPUT_ID:
            currentFocusedInput.value = CONFIRM_PASSWORD_INPUT_ID;
            currentFocusedValue.value = confirmPassword;
            break;
    }
};

const handleInput = function handleKeypadInput(value: string) {
    const id = currentFocusedInput;
    switch (id.value) {
        case OLD_PASSWORD_INPUT_ID:
            oldPassword.value = value;
            break;
        case NEW_PASSWORD_INPUT_ID:
            newPassword.value = value;
            break;
        case CONFIRM_PASSWORD_INPUT_ID:
            confirmPassword.value = value;
            break;
    }
};

// Handle I/O input
const handleInputOldPassword = function (value: string) {
    oldPassword.value = value;
};
const handleInputNewPassword = function (value: string) {
    newPassword.value = value;
};
const handleInputConfirmPassword = function (value: string) {
    confirmPassword.value = value;
};

// Change the student password asynchronously
const { fetchData: updateStudentPw, isLoading } = useAxios<null>(
    null,
    services.updateStudentPw
);

const handleSubmit = function onHandleUpdateStudentPw() {
    if (!studentRegexes.password.reg.test(oldPassword.value)) {
        toastCenterErrorMessage('기존 비밀번호는 4자리 숫자입니다');
        return;
    }
    if (!studentRegexes.password.reg.test(newPassword.value)) {
        toastCenterErrorMessage('새 비밀번호를 4자리 숫자로 입력해주세요');
        return;
    }
    if (!(newPassword.value == confirmPassword.value)) {
        toastCenterErrorMessage('새 비밀번호가 일치하지 않습니다');
        return;
    }

    updateStudentPw(
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
    <VLoading v-if="isLoading" color="kiosk-primary" class="kiosk-loading" />
    <div class="kiosk-pw-change-form">
        <form class="kiosk-pw-change-form__form" @submit.prevent="handleSubmit">
            <VInput
                id="kiosk-student-old-password"
                label="현재 비밀번호"
                name="oldPassword"
                type="password"
                size="xl"
                color="kiosk-primary"
                :isFocus="currentFocusedInput === OLD_PASSWORD_INPUT_ID"
                :value="oldPassword"
                :maxlength="4"
                @input="handleInputOldPassword"
                @focus="handleFocus" />
            <VInput
                id="kiosk-student-new-password"
                label="새 비밀번호"
                name="newPassword"
                type="password"
                size="xl"
                color="kiosk-primary"
                :isFocus="currentFocusedInput === NEW_PASSWORD_INPUT_ID"
                :value="newPassword"
                :maxlength="4"
                @input="handleInputNewPassword"
                @focus="handleFocus" />
            <VInput
                id="kiosk-student-confirm-password"
                label="비밀번호 확인"
                name="confirmPassword"
                type="password"
                size="xl"
                color="kiosk-primary"
                :isFocus="currentFocusedInput === CONFIRM_PASSWORD_INPUT_ID"
                :value="confirmPassword"
                :maxlength="4"
                @input="handleInputConfirmPassword"
                @focus="handleFocus" />
            <VButton text="변경하기" type="submit" color="green" size="lg" />
        </form>
        <TheKeypad :value="currentFocusedValue.value" @input="handleInput" />
    </div>
</template>

<style lang="scss">
.kiosk-pw-change-form {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: minmax(0, 1fr) auto;
    gap: 1rem;
}

.kiosk-pw-change-form__form {
    justify-self: center;
    align-self: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    gap: 2rem;
}
</style>
