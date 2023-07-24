<script setup lang="ts">
import InputGuide from '@/components/kiosk/InputGuide.vue';
import TheInput from '@/components/common/TheInput.vue';
import TheKeypad from '@/components/kiosk/TheKeypad.vue';
import TheModal from '@/components/common/TheModal.vue';

import { onBeforeMount, ref } from 'vue';

const emit = defineEmits(['before-mount']);
const studentInfo = ref('');
const isModalOpen = ref(false);

onBeforeMount(() => {
    emit('before-mount', { title: '출석 확인', routeName: 'kiosk' });
});

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

const handleModal = function closeModal() {
    isModalOpen.value = false;
    studentInfo.value = '';
};

const handleSubmit = function checkAttendance() {
    // Todo 정규식 검사
    isModalOpen.value = true;
};
</script>

<template>
    <div>
        <InputGuide />
        <TheInput
            type="text"
            refer="학년반번호"
            :value="studentInfo"
            @update-input="handleChange"
            @submit="handleSubmit" />
        <button @click="handleSubmit">출석하기</button>
        <TheKeypad @input="handleInput" @delete="deleteInput" />
        <!-- Modal -->
        <teleport to="#teleport">
            <TheModal v-show="isModalOpen" @close-modal="handleModal">
                <template #modal-content>
                    <div>학년반번호 맞으시죠? {{ studentInfo }}</div>
                </template>
            </TheModal>
        </teleport>
    </div>
</template>

<style lang="scss"></style>
