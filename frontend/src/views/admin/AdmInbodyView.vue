<script setup lang="ts">
import InbodySearchBar from '@/components/admin/inbody/InbodySearchBar.vue';
import StudentTable from '@/components/admin/student/StudentTable.vue';
import InbodyDateTable from '@/components/admin/inbody/InbodyDateTable.vue';

import { ref, computed } from 'vue';
import { getInbodys } from '@/apis/services/inbodys';
import type { Ref } from 'vue';
import type { Inbody } from '@/types/inbody.interace';
import router from '@/router';
import { calculateDays, createIndexTable } from '@/utils/inbody';

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
    let arry = new Array(students.value.length);
    for (let i = 0; i < arry.length; i++) {
        arry[i] = new Array(days.value).fill('-');
    }
    return arry;
});

const query = computed(() => {
    return {
        startDate: startDate.value,
        endDate: endDate.value,
        grade: grade.value,
        room: room.value,
        number: number.value,
        name: name.value,
    };
});

const handleSubmit = function searchAttendance() {
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

        // inbodyList에 날짜 데이터 넣기
        for (let i = 0; i < students.value.length; i++) {
            for (let j = 0; j < students.value[i].inbodySet.length; j++) {
                inbodyList.value[i][
                    dateIndexTable.value[
                        students.value[i].inbodySet[j].testDate
                    ]
                ] = students.value[i].inbodySet[j].testDate;
            }
        }
    });
};

const handleStudentClick = function goStudentInbodyList(student: any) {
    router.push({
        name: 'admin-inbody-student',
        params: {
            grade: student.grade,
            room: student.room,
            number: student.number,
            name: student.name,
        },
        query: {
            start: startDate.value,
            end: endDate.value,
        },
    });
};

const handleInbodyClick = function goInbodyDetail(i: number, j: number) {
    console.log(students.value[i]);
};
</script>

<template>
    <div class="admin-inbody">
        <div>인바디 관리</div>
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
            <StudentTable :students="students" />

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
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-inbody-content {
    display: flex;
    overflow-y: auto;
}

.admin-inbody-date {
    height: fit-content;
    overflow-x: auto;
}
</style>
