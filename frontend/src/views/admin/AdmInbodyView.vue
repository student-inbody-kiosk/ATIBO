<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodySearchBar from '@/components/admin/inbody/InbodySearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import InbodyDateTable from '@/components/admin/inbody/InbodyDateTable.vue';
import VLoading from '@/components/common/VLoading.vue';

import router from '@/router';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { calculateDays, createIndexTable } from '@/utils/inbody';
import { useQueryStore } from '@/stores/query.store';
import { checkSearchInput } from '@/utils/checkInput';
import { ref, computed, onMounted } from 'vue';
import { toastTopErrorMessage } from '@/utils/toastManager';

import type { Ref } from 'vue';
import type { Inbody } from '@/types/inbody.interace';

const queryStore = useQueryStore();
const { fetchData: getInbodys, isLoading } = useAxios(
    null,
    services.getInbodys
);

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

// 인바디 테이블 생성 및 데이터 할당
const createTable = (startDate, endDate, data) => {
    days.value = calculateDays(startDate, endDate);
    dateIndexTable.value = createIndexTable(startDate, endDate);
    students.value = data;

    // inbodyList에 데이터 넣기
    for (let i = 0; i < data.length; i++) {
        for (let j = 0; j < data[i].inbodySet.length; j++) {
            inbodyList.value[i][
                dateIndexTable.value[data[i].inbodySet[j].testDate]
            ] = data[i].inbodySet[j];
        }
    }
};

onMounted(() => {
    const {
        startDate: start,
        endDate: end,
        grade: g,
        room: r,
        number: num,
        name: n,
    } = queryStore.routeQuery;
    if (!start && !end) return;
    if (!g && !r && !num && !n) return;
    startDate.value = start ? start : '';
    endDate.value = end ? end : '';
    grade.value = g ? String(g) : '';
    room.value = r ? String(r) : '';
    number.value = num ? String(num) : '';
    name.value = n ? n : '';
    getInbodys(start, end, g, r, num, n).then((res) => {
        createTable(start, end, res);
    });
});

// 검색
const handleSubmit = function searchAttendance() {
    if (
        !grade.value.trim() &&
        !room.value.trim() &&
        !number.value.trim() &&
        !name.value.trim()
    ) {
        toastTopErrorMessage('검색 조건을 입력해주세요');
        return;
    }

    const data = {
        startDate: startDate.value,
        endDate: endDate.value,
        grade: grade.value,
        room: room.value,
        number: number.value,
        name: name.value,
    };
    if (checkSearchInput(data)) return;

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

    getInbodys(
        startDate.value,
        endDate.value,
        parsedGrade,
        parsedRoom,
        parsedNumber,
        name.value
    ).then((res) => {
        createTable(startDate.value, endDate.value, res);
    });
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
    <VLoading v-if="isLoading" color="admin-primary" />
    <div v-else class="admin-inbody">
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
            @search="handleSubmit"
            @enter="handleSubmit" />

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
