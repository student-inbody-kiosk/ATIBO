<script setup lang="ts">
import { onBeforeMount, ref, inject } from 'vue';
import ToastManager from '@/utils/ToastManager';

const emit = defineEmits(['update-header']);
const studentInfo = ref('');
const isModalOpen = ref(false);

onBeforeMount(() => {
    emit('update-header', { title: '출석 확인', routeName: 'kiosk-index' });
});

const deleteInput = function deleteStudentInfo() {
    if (studentInfo.value.length == 0) return;
    studentInfo.value = studentInfo.value.slice(0, -1);
};

const handleModal = function closeModal() {
    isModalOpen.value = false;
    studentInfo.value = '';
};

const handleSubmit = function checkAttendance() {
    // Todo 정규식 검사
    isModalOpen.value = true;
};

const toast = inject('toast');

const handleClick = () => {
    toast('Hello, this is a toast message!', 'success', 100000);
};
</script>

<template>
    <div @click="handleClick">빵</div>
</template>

<style lang="scss"></style>
@/plugins/ToastManager
