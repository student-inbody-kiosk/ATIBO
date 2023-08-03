<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import TheModal from '@/components/common/TheModal.vue';
import KioInputGuide from '@/components/kiosk/KioInputGuide.vue';
import KioStudentForm from '@/components/kiosk/KioStudentForm.vue';
import KioAttendModal from '@/components/kiosk/attendance/KioAttendModal.vue';
import type { HeaderUpdate } from '@/types/app.interface';
import type { StudentSimple } from '@/types/students.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// student info
const student = ref<StudentSimple | null>(null);

// udpate student info
const handleUpdateStudent = function (value: StudentSimple) {
    student.value = value;
};

// close modal
const handleCloseModal = function () {
    student.value = null;
};

// update kio-header
onBeforeMount(() => {
    emit('update-header', { title: '출석 확인', routeName: 'kiosk-index' });
});
</script>

<template>
    <div class="kiosk-attend-view">
        <KioInputGuide>
            <p>
                학년, 반, 번호를 입력해주세요 <br />
                예시 1학년 1반 1번 -> 10101
            </p></KioInputGuide
        >
        <KioStudentForm @update-student="handleUpdateStudent" />
        <!-- modal -->
        <TheModal v-if="student" @close-modal="handleCloseModal">
            <KioAttendModal
                :student="student"
                @close-modal="handleCloseModal" />
        </TheModal>
    </div>
</template>

<style lang="scss">
.kiosk-attend-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr fit-content;
    gap: 5rem;
    padding: 1rem 2rem;
    height: 100%;
}
</style>
