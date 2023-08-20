<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import AttendSearchBar from '@/components/admin/attendance/AttendSearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import AttendTable from '@/components/admin/attendance/AttendTable.vue';
import VLoading from '@/components/common/VLoading.vue';

import { toastTopErrorMessage } from '@/utils/toastManager';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { checkSearchInput } from '@/utils/checkInput';
import { ref } from 'vue';

import type { Ref } from 'vue';
import type { StudentAttendance } from '@/types/attendance.interface';

import { useMeta } from 'vue-meta';

useMeta({
    title: 'ATIBO 아티보 학생 출결 관리',
    description: 'ATIBO 아티보 학생 출결 관리 페이지',
});

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const students: Ref<StudentAttendance[]> = ref([]);

// 현재 날짜 YYYY-MM 형식으로 반환
const handleMonth = function getCurrentMonth(): string {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = String(currentDate.getMonth() + 1).padStart(2, '0');

    return `${year}-${month}`;
};
const date = ref(handleMonth());

const { fetchData: getAttendances, isLoading } = useAxios(
    null,
    services.getAttendances
);

const handleSubmit = function searchAttendance() {
    if (!grade.value && !room.value && !number.value && !name.value) {
        toastTopErrorMessage('검색 조건을 입력해주세요');
        return;
    }

    const data = {
        date: date.value,
        grade: grade.value,
        room: room.value,
        name: name.value,
        number: number.value,
    };
    if (checkSearchInput(data)) return;

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
    <VLoading v-if="isLoading" color="admin-primary" />
    <div v-else class="admin-attend">
        <div class="admin-attend__header">
            <VButton
                text="뒤로"
                color="gray"
                @click="$router.push({ name: 'admin-main' })" />
            <h1>출결 관리</h1>
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
            @search="handleSubmit"
            @enter="handleSubmit" />

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
    align-items: center;
    padding-bottom: 1rem;

    h1 {
        font-size: 1.5rem;
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
