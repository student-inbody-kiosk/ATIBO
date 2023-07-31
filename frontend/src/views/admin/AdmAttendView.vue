<script setup lang="ts">
import TheInput from '@/components/common/TheInput.vue';
import TheButton from '@/components/common/TheButton.vue';
import AttendDataLabel from '@/components/admin/AttendDataLabel.vue';
import AttendData from '@/components/admin/AttendData.vue';

import { getAttendances } from '@/apis/services/attendances';
import { ref, computed } from 'vue';

import type { Ref } from 'vue';
import type { Attendance, AttendanceInfo } from '@/types/admin.interface';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const month = ref('');
const students: Ref<AttendanceInfo> = ref([]);
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
        month.value,
        Number(grade.value),
        Number(room.value),
        Number(number.value),
        name.value
    ).then((res) => {
        students.value = res?.data;
    });
};
</script>

<template>
    <div class="admin-attend">
        <div class="admin-attend__header">출결 관리</div>
        <section class="admin-attend__searchbar">
            <div class="admin-attend__searchbar-date">
                <TheInput
                    label="월별 검색"
                    type="month"
                    refer="month"
                    :value="month"
                    @update-input="(value) => (month = value)" />
            </div>
            <div class="admin-attend__searchbar-student">
                <TheInput
                    label="학년"
                    type="text"
                    refer="grade"
                    :value="grade"
                    @update-input="(value) => (grade = value)" />
                <TheInput
                    label="반"
                    type="text"
                    refer="room"
                    :value="room"
                    @update-input="(value) => (room = value)" />
                <TheInput
                    label="번호"
                    type="text"
                    refer="number"
                    :value="number"
                    @update-input="(value) => (number = value)" />
                <TheInput
                    label="이름"
                    type="text"
                    refer="name"
                    :value="name"
                    @update-input="(value) => (name = value)" />
                <TheButton
                    text="조회"
                    color="admin-primary"
                    size="md"
                    emitMessage="submit"
                    @submit="handleSubmit" />
            </div>
        </section>

        <section class="admin-attend-list">
            <div class="admin-attend-list__student">
                <table>
                    <AttendDataLabel class="admin-attend-list__student__head" />
                    <tbody>
                        <AttendData
                            v-for="(student, index) in students"
                            :key="student.name"
                            :index="index"
                            :grade="student.grade"
                            :room="student.room"
                            :number="student.room"
                            :name="student.name" />
                    </tbody>
                </table>
            </div>
            <div class="admin-attend-list__attend">
                <table>
                    <thead class="admin-attend-list__attend__head">
                        <tr>
                            <th
                                class="student-attend-list__attend__label"
                                v-for="index in 31"
                                :key="index">
                                {{ index }}
                            </th>
                        </tr>
                    </thead>
                    <tbody class="hi">
                        <tr v-for="(student, index) in students" :key="index">
                            <td
                                class="student-attend-list__attend__content"
                                v-for="index in 31"
                                :key="index">
                                -
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
</template>

<style lang="scss">
.admin-attend__searchbar-date,
.admin-attend__searchbar-student {
    display: flex;
}
.admin-attend-list {
    display: flex;
    width: 100%;
    height: 36rem;
    overflow-y: auto;
}

.admin-attend-list__student__head {
    tr,
    th {
        @include z-index(label);
        position: sticky;
        top: 0;
    }
}

.admin-attend-list__attend {
    width: 40rem;
    height: fit-content;
    overflow-x: auto;
}

.student-attend-list__attend__label,
.student-attend-list__attend__content {
    min-width: 3rem;
    margin-top: 0.2rem;
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    text-align: center;
}

.student-attend-list__attend__label {
    background-color: $admin-tertiary;
}
.student-attend-list__attend__content {
    background-color: $white;
}
</style>
