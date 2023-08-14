<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import AttendSearchBar from '@/components/admin/attendance/AttendSearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import AttendTable from '@/components/admin/attendance/AttendTable.vue';
import { getAttendances } from '@/apis/services/attendances';
import { ref } from 'vue';

import type { Ref } from 'vue';
import type { StudentAttendance } from '@/types/attendance.interface';
import { toastTopErrorMessage } from '@/utils/toastManager';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const date = ref('');
const students: Ref<StudentAttendance[]> = ref([]);

const handleSubmit = function searchAttendance() {
    if (!date.value) {
        toastTopErrorMessage('조회할 날짜를 입력해주세요');
        return;
    }

    if (!grade.value && !room.value && !number.value && !name.value) {
        toastTopErrorMessage('검색 조건을 입력해주세요');
        return;
    }

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
        <div class="admin-attend__header">
            <VButton
                text="뒤로"
                color="gray"
                @click="$router.push({ name: 'admin-main' })" />
            <div>출결 관리</div>
        </div>
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
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-attend__header {
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: auto minmax(0, 1fr);
    padding-bottom: 1rem;

    div {
        font-size: 1.4rem;
        font-weight: 600;
        text-align: center;
    }
}
.admin-attend-content {
    display: flex;
    overflow-y: auto;

    table {
        height: fit-content;
    }
}

.admin-attend-attendance {
    height: fit-content;
    overflow-x: auto;
}
</style>
