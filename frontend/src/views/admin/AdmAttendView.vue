<script setup lang="ts">
import AttendSearchBar from '@/components/admin/attendance/AttendSearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import AttendTable from '@/components/admin/attendance/AttendTable.vue';
import { getAttendances } from '@/apis/services/attendances';
import { ref, computed } from 'vue';

import type { Ref } from 'vue';
import type { StudentAttendance } from '@/types/attendance.interface';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const date = ref('');
const students: Ref<StudentAttendance[]> = ref([]);

const query = computed(() => {
    return {
        grade: grade.value,
        room: room.value,
        number: number.value,
        name: name.value,
    };
});

const handleSubmit = function searchAttendance() {
    getAttendances(
        date.value,
        Number(grade.value),
        Number(room.value),
        Number(number.value),
        name.value
    ).then((res) => {
        students.value = res;
    });
};
</script>

<template>
    <div class="admin-attend">
        <div class="admin-attend__header">출결 관리</div>
        <AttendSearchBar
            :date="date"
            :grade="grade"
            :room="room"
            :number="number"
            :name="name"
            @date="(value: string) => (date = value)"
            @grade="(value: string) => (grade = value)"
            @room="(value: string) => (room = value)"
            @number="(value: string) => (number = value)"
            @name="(value: string) => (name = value)"
            @search="handleSubmit" />

        <section class="admin-attend-content">
            <StudentTable :students="students" />
            <div class="admin-attend-attendance">
                <AttendTable :students="students" />
            </div>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-attend {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-attend-content {
    display: flex;
    overflow-y: auto;
}

.admin-attend-attendance {
    height: fit-content;
    overflow-x: auto;
}
</style>
