<script setup lang="ts">
import { ref, onBeforeMount } from 'vue';
import TheModal from '@/components/common/TheModal.vue';
import KioInputGuide from '@/components/kiosk/KioInputGuide.vue';
import KioStudentForm from '@/components/kiosk/KioStudentForm.vue';
import KioAttendModal from '@/components/kiosk/attendance/KioAttendModal.vue';
import type { HeaderUpdate } from '@/types/app.interface';
import type { StudentSimple } from '@/types/students.interface';
import { useMeta } from 'vue-meta';

useMeta({
    title: 'ATIBO 아티보 출결 키오스크',
    description: 'ATIBO 아티보 학생 출결 등록 키오스크',
});
const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// Update kio-header
onBeforeMount(() => {
    emit('update-header', {
        title: '출석 확인',
        routeName: 'kiosk-index',
        routeParams: {},
        routeQuery: {},
    });
});

/* Handle modal with student data */
const student = ref<StudentSimple | null>(null);

// Update student & Open modal
const handleUpdateStudent = function openKioAttendModal(value: StudentSimple) {
    student.value = value;
};

// Delete student & Close modal
const handleCloseModal = function closeKioAttendModal() {
    student.value = null;
};
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
    </div>
    <!-- modal -->
    <TheModal v-if="student" @close-modal="handleCloseModal">
        <KioAttendModal :student="student" @close-modal="handleCloseModal" />
    </TheModal>
</template>

<style lang="scss">
.kiosk-attend-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr auto;
    row-gap: 4rem;
    height: 100%;
    width: 100%;
    padding: 1rem 2rem;
}
</style>
