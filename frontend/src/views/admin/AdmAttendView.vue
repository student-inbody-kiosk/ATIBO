<script setup lang="ts">
import AttendSearchbar from '@/components/admin/AttendSearchbar.vue';
import StudentDataLabel from '@/components/admin/student/StudentDataLabel.vue';
import StudentData from '@/components/admin/student/StudentData.vue';

import { getAttendances } from '@/apis/services/attendances';
import { ref, computed } from 'vue';

import type { Ref } from 'vue';
import type { AttendanceInfo } from '@/types/admin.interface';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const date = ref('');
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
        date.value,
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
        <AttendSearchbar
            :date="date"
            :grade="grade"
            :room="room"
            :number="number"
            :name="name"
            @date="(value) => (date = value)"
            @grade="(value) => (grade = value)"
            @room="(value) => (room = value)"
            @number="(value) => (number = value)"
            @name="(value) => (name = value)"
            @search="handleSubmit" />

        <section class="admin-attend-list">
            <div class="admin-attend-list__student">
                <table>
                    <StudentDataLabel
                        class="admin-attend-list__student__head" />
                    <tbody>
                        <StudentData
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
