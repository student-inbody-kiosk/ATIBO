<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodySearchBar from '@/components/admin/inbody/InbodySearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import InbodyDateTable from '@/components/admin/inbody/InbodyDateTable.vue';
import { toastTopErrorMessage } from '@/utils/toastManager';

import router from '@/router';
import { ref, computed, onMounted } from 'vue';
import { getInbodys } from '@/apis/services/inbodys';
import { calculateDays, createIndexTable } from '@/utils/inbody';
import { useQueryStore } from '@/stores/query.store';

import type { Ref } from 'vue';
import type { Inbody } from '@/types/inbody.interace';

const queryStore = useQueryStore();

onMounted(() => {
    const { startDate, endDate, grade, room, number, name } =
        queryStore.routeQuery;
    if (!startDate && !endDate) return;
    if (!grade && !room && !number && !name) return;
    getInbodyAPI(startDate, endDate, grade, room, number, name);
});

// 데이터 요청 후 table 생성 및 데이터 할당
const getInbodyAPI = (
    startDate: string,
    endDate: string,
    grade: number,
    room: number,
    number: number,
    name: string
) => {
    getInbodys(startDate, endDate, grade, room, number, name).then((res) => {
        days.value = calculateDays(startDate, endDate);
        dateIndexTable.value = createIndexTable(startDate, endDate);
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

// 검색
const handleSubmit = function searchAttendance() {
    if (!startDate.value || !endDate.value) {
        toastTopErrorMessage('검색 기간을 입력해주세요');
        return;
    }

    if (!grade.value && !room.value && !number.value && !name.value) {
        toastTopErrorMessage('검색 조건을 입력해주세요');
        return;
    }

    const parsedGrade = Number(grade.value);
    const parsedRoom = Number(room.value);
    const parsedNumber = Number(number.value);

    queryStore.updateQuery({
        startDate: startDate.value,
        endDate: endDate.value,
        grade: parsedGrade,
        room: parsedRoom,
        number: parsedNumber,
        name: name.value,
    });

    getInbodyAPI(
        startDate.value,
        endDate.value,
        parsedGrade,
        parsedRoom,
        parsedNumber,
        name.value
    );
};

const handleStudentClick = function goStudentInbodyList(student: any) {
    const { grade, room, number, name } = student;

    const { startDate, endDate } = queryStore.routeQuery;

    router.push({
        name: 'admin-inbody-student',
        params: { grade, room, number, name },
        query: {
            start: startDate,
            end: endDate,
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
        <div class="admin-inbody__header">
            <VButton
                text="뒤로"
                color="gray"
                @click="$router.push({ name: 'admin-main' })" />
            <div>인바디 관리</div>
        </div>
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
