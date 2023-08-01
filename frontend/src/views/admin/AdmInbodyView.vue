<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import AttendDataLabel from '@/components/admin/AttendDataLabel.vue';
import AttendData from '@/components/admin/AttendData.vue';

import { ref, computed } from 'vue';
import { getInbodys } from '@/apis/services/inbodys';

import type { Ref } from 'vue';
import type { Inbody } from '@/types/admin.interace';

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

// 시작 ~ 끝 날짜를 기준으로 일수를 반환하는 함수
const handleDateInput = function calculateDays() {
    const start = new Date(startDate.value);
    const end = new Date(endDate.value);

    const timeDifference = end.getTime() - start.getTime();

    const oneDayInMs = 24 * 60 * 60 * 1000;
    const daysDifference = Math.round(timeDifference / oneDayInMs);

    days.value = daysDifference + 1;
};

// 시작 ~ 끝 날짜를 기준으로 인덱스 테이블 생성
const handleInbodyIndex = function createIndexTable() {
    const start = new Date(startDate.value);
    const end = new Date(endDate.value);

    const indexTable: { [date: string]: number } = {};
    const currentDate = new Date(start);
    let index = 1;

    // 인덱스 테이블 생성 ('-'로 초기화)
    while (currentDate <= end) {
        const formattedDate = currentDate.toISOString().slice(0, 10);
        indexTable[formattedDate] = index - 1;
        currentDate.setDate(currentDate.getDate() + 1);
        index++;
    }
    dateIndexTable.value = indexTable;
};

const handleSubmit = function searchAttendance() {
    getInbodys(
        startDate.value,
        endDate.value,
        Number(grade.value),
        Number(room.value),
        Number(number.value),
        name.value
    ).then((res) => {
        handleDateInput();
        handleInbodyIndex();
        students.value = res?.data;

        // inbodyList에 인바디 데이터 넣기
        for (let i = 0; i < students.value.length; i++) {
            for (let j = 0; j < students.value[i].inbodySet.length; j++) {
                inbodyList.value[i][
                    dateIndexTable.value[
                        students.value[i].inbodySet[j].testDate
                    ]
                ] = students.value[i].inbodySet[j].testDate;
            }
        }
        console.log(dateIndexTable.value);
    });
};

const handleStudentClick = function goStudentInbodyList(student: any) {
    console.log(student);
};

const handleInbodyClick = function goInbodyDetail(i: number, j: number) {
    console.log(students.value[i].inbodySet[j]);
};
</script>

<template>
    <div>
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
                    size="md"
                    @click="handleSubmit" />
            </div>
        </section>
        <section class="admin-inbody-list">
            <div class="admin-inbody-list__student">
                <table>
                    <AttendDataLabel
                        class="admin-inbody-list__student__label" />
                    <tbody>
                        <AttendData
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
            <div class="admin-inbody-list__inbody">
                <table>
                    <thead>
                        <tr>
                            <th :colspan="days">인바디 기록</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(student, i) in students" :key="i">
                            <td
                                v-for="j in days"
                                :key="j"
                                @click="() => handleInbodyClick(i, j - 1)">
                                {{ inbodyList[i][j - 1] }}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody__searchbar-date,
.admin-inbody__searchbar-student {
    display: flex;
}

.admin-inbody-list {
    display: flex;
    // width: 100%;
    height: 36rem;
    overflow-y: auto;
}

// .admin-inbody-list__student__label {
//     tr,
//     th {
//         @include z-index(label);
//         position: sticky;
//         top: 0;
//     }
// }

.admin-inbody-list__inbody {
    width: 48rem;
    height: fit-content;
    overflow-x: auto;

    th {
        width: 48rem;
    }

    td {
        min-width: 6rem;
    }
}

tr,
th {
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    background-color: $admin-tertiary;
}

td {
    margin-top: 0.2rem;
    padding: 0.2rem;
    height: auto;
    border: 0.1rem solid $admin-secondary;
    background-color: $white;
    text-align: center;
}
</style>
