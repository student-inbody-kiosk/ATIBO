<script setup lang="ts">
import StudentSearchbar from '@/components/admin/student/StudentSearchbar.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailData from '@/components/admin/student/StudentDetailData.vue';
import { ref, computed } from 'vue';
import { getStudents } from '@/apis/services/students';
import type { Ref } from 'vue';
import type { StudentDetail } from '@/types/students.interface';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const students: Ref<StudentDetail[]> = ref([]);
const query = computed(() => {
    return {
        grade: grade.value,
        room: room.value,
        number: number.value,
        name: name.value,
    };
});

const handleSubmit = function searchStudents() {
    // TODO: check regular expression
    getStudents(
        parseInt(grade.value),
        parseInt(room.value),
        parseInt(number.value),
        name.value
    ).then((res) => {
        students.value = res?.data;
    });
};
</script>

<template>
    <div class="admin-student">
        <div class="admin-student__header">학생 관리</div>
        <StudentSearchbar
            :grade="grade"
            :room="room"
            :number="number"
            :name="name"
            :query="query"
            :isStudentData="Boolean(students.length)"
            @grade="(value) => (grade = value)"
            @room="(value) => (room = value)"
            @number="(value) => (number = value)"
            @name="(value) => (name = value)"
            @search="handleSubmit" />

        <section class="admin-student-list">
            <table>
                <StudentDetailDataLabel />
                <tbody>
                    <StudentDetailData
                        v-for="(data, index) in students"
                        :key="data.id"
                        :id="index"
                        :grade="data.grade"
                        :room="data.room"
                        :number="data.number"
                        :name="data.name"
                        :sex="data.sex"
                        :birthDate="data.birthDate"
                        :password="data.password" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-list {
    overflow: auto;
}

table {
    width: 100%;
}
</style>
