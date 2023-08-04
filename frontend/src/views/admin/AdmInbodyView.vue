<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentData from '@/components/admin/StudentData.vue';
import InbodyDateTable from '@/components/admin/InbodyDateTable.vue';

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
        students.value = res?.data;

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
        <section class="admin-inbody__searchbar">
            <div class="admin-inbody__searchbar-date">
                <VInput
                    id="startDate"
                    label="시작"
                    type="date"
                    refer="startDate"
                    :value="startDate"
                    @input="(value) => (startDate = value)" />
                <VInput
                    id="endDate"
                    label="끝"
                    type="date"
                    refer="endDate"
                    :value="endDate"
                    @input="(value) => (endDate = value)" />
            </div>
            <div class="admin-inbody__searchbar-student">
                <VInput
                    id="grade"
                    label="학년"
                    refer="grade"
                    :value="grade"
                    @input="(value) => (grade = value)" />
                <VInput
                    id="room"
                    label="반"
                    refer="room"
                    :value="room"
                    @input="(value) => (room = value)" />
                <VInput
                    id="number"
                    label="번호"
                    refer="number"
                    :value="number"
                    @input="(value) => (number = value)" />
                <VInput
                    id="name"
                    label="이름"
                    refer="name"
                    :value="name"
                    @input="(value) => (name = value)" />
                <VButton
                    text="조회"
                    color="admin-primary"
                    @click="handleSubmit" />
            </div>
        </section>
        <section class="admin-inbody-list">
            <div class="admin-inbody-list__student">
                <table>
                    <StudentDataLabel />
                    <tbody>
                        <StudentData
                            v-for="(student, index) in students"
                            :key="index"
                            :index="index"
                            :grade="student.grade"
                            :room="student.room"
                            :number="student.number"
                            :name="student.name"
                            @click="() => handleStudentClick(student)" />
                    </tbody>
                </table>
            </div>

            <InbodyDateTable
                :days="days"
                :students="students"
                :inbodyList="inbodyList"
                @click="handleInbodyClick" />
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
}

.admin-inbody__searchbar-date,
.admin-inbody__searchbar-student {
    display: flex;
}

.admin-inbody-list {
    display: flex;
    height: 36rem;
    overflow-y: auto;
}
</style>
