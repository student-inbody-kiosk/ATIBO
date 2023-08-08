<script setup lang="ts">
import InbodySearchBar from '@/components/admin/inbody/InbodySearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import InbodyDateTable from '@/components/admin/inbody/InbodyDateTable.vue';
import { toastTopErrorMessage } from '@/utils/toastManager';

import router from '@/router';
import { ref, computed } from 'vue';
import { getInbodys } from '@/apis/services/inbodys';
import { calculateDays, createIndexTable } from '@/utils/inbody';

import type { Ref } from 'vue';
import type { Inbody } from '@/types/inbody.interace';

const startDate = ref('');
const endDate = ref('');
const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');

const students: Ref<Inbody[]> = ref([]);

const days = ref(0);
const dateIndexTable: Ref<{ [date: string]: number }> = ref({});
const inbodyList = computed(() => {
    const arry = Array.from(
        Array(students.value.length),
        () => new Array(days.value)
    );
    return arry;
});

const handleSubmit = function searchAttendance() {
    if (!startDate.value || !endDate.value) {
        toastTopErrorMessage('검색 기간을 입력해주세요');
        return;
    }

    if (!grade.value && !room.value && !number.value && !name.value) {
        toastTopErrorMessage('검색 조건을 입력해주세요');
        return;
    }

    getInbodys(
        startDate.value,
        endDate.value,
        Number(grade.value),
        Number(room.value),
        Number(number.value),
        name.value
    ).then((res) => {
        days.value = calculateDays(startDate.value, endDate.value);
        dateIndexTable.value = createIndexTable(startDate.value, endDate.value);
        students.value = res;

        // inbodyList에 데이터 넣기
        for (let i = 0; i < students.value.length; i++) {
            for (let j = 0; j < students.value[i].inbodySet.length; j++) {
                inbodyList.value[i][
                    dateIndexTable.value[
                        students.value[i].inbodySet[j].testDate
                    ]
                ] = students.value[i].inbodySet[j];
            }
        }
    });
};

const handleStudentClick = function goStudentInbodyList(student: any) {
    const { grade, room, number, name } = student;

    router.push({
        name: 'admin-inbody-student',
        params: { grade, room, number, name },
        query: {
            start: startDate.value,
            end: endDate.value,
        },
    });
};

const handleInbodyClick = function goInbodyDetail(i: number, j: number) {
    const { grade, room, number, name } = students.value[i];
    const inbodyId = inbodyList.value[i][j].id;

    router.push({
        name: 'admin-inbody-detail',
        params: { grade, room, number, name, inbodyId },
    });
};
</script>

<template>
    <div class="admin-inbody">
        <div class="admin-inbody__header">인바디 관리</div>
        <InbodySearchBar
            :startDate="startDate"
            :endDate="endDate"
            :grade="grade"
            :room="room"
            :number="number"
            :name="name"
            @start-date="(value) => (startDate = value)"
            @end-date="(value) => (endDate = value)"
            @grade="(value: string) => (grade = value)"
            @room="(value: string) => (room = value)"
            @number="(value: string) => (number = value)"
            @name="(value: string) => (name = value)"
            @search="handleSubmit" />

        <section class="admin-inbody-content">
            <StudentTable :students="students" @click="handleStudentClick" />

            <div class="admin-inbody-date">
                <InbodyDateTable
                    :days="days"
                    :students="students"
                    :inbodyList="inbodyList"
                    @click="handleInbodyClick" />
            </div>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-inbody__header {
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 1rem;
}

.admin-inbody-content {
    width: 100%;
    display: flex;
    overflow-y: auto;
}

.admin-inbody-date {
    width: 80%;
    height: fit-content;
    overflow-x: auto;
}
</style>
