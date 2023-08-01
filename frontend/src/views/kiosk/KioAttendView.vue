<script setup lang="ts">
import KioCheckStudent from '@/components/kiosk/KioCheckStudent.vue';
import KioInputGuide from '@/components/kiosk/KioInputGuide.vue';
import TheModal from '@/components/common/TheModal.vue';
import type { StudentSimple } from '@/types/students.interface';
import { onBeforeMount, ref } from 'vue';
import KioAttendModal from '@/components/kiosk/attendance/KioAttendModal.vue';
import type { Header } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: Header): void;
}>();

onBeforeMount(() => {
    emit('update-header', { title: '출석 확인', routeName: 'kiosk-index' });
});

const studentInfo = ref<StudentSimple | null>(null);

const handleStudentInfo = function (value: StudentSimple) {
    studentInfo.value = value;
};

const handleCloseModal = function () {
    studentInfo.value = null;
};
</script>

<template>
    <div class="kiosk-attend-view">
        <KioInputGuide />
        <KioCheckStudent @student-info="handleStudentInfo" />
        <!-- modal -->
        <TheModal v-if="studentInfo" @close-modal="handleCloseModal">
            <KioAttendModal
                :studentInfo="studentInfo"
                @close-modal="handleCloseModal" />
        </TheModal>
    </div>
</template>

<style lang="scss">
.kiosk-attend-view {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 1rem 2rem;
    height: 100%;
}
</style>
