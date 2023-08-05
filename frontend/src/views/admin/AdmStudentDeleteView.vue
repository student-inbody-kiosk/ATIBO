<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailData from '@/components/admin/student/StudentDetailData.vue';

import { useRoute } from 'vue-router';
import { ref, onBeforeMount } from 'vue';

import { getStudents, deleteStudents } from '@/apis/services/students';

import type { Ref } from 'vue';

import type { Student } from '@/apis/types/students.interface';
import router from '@/router';

const route = useRoute();
const students: Ref<Student[]> = ref([]);
const deleteList: Ref<{ [index: string]: number }> = ref({});

onBeforeMount(() => {
    const { grade, room, number, name } = route.query;
    getStudents(Number(grade), Number(room), Number(number), String(name)).then(
        (res) => (students.value = res?.data)
    );
});

const handleCheckClick = function selectStudent(studentId: string) {
    console.log(studentId);
    if (studentId in deleteList.value) {
        return delete deleteList.value[studentId];
    }
    deleteList.value[studentId] = 1;
};

const handleDeleteClick = function deleteStudent() {
    const ids = Object.keys(deleteList.value);
    deleteStudents(ids).then(() => {
        router.push({ name: 'admin-student', query: route.query });
    });
};
</script>

<template>
    <div class="admin-student-delete">
        <div>학생 삭제</div>

        <VButton text="삭제" color="red" @click="handleDeleteClick" />

        <section class="admin-student-list">
            <table>
                <StudentDetailDataLabel />
                <tbody>
                    <StudentDetailData
                        v-for="(student, index) in students"
                        :isDelete="true"
                        :key="student.id"
                        :id="index"
                        :grade="student.grade"
                        :room="student.room"
                        :number="student.number"
                        :name="student.name"
                        :sex="student.sex"
                        :birthDate="student.birthDate"
                        :password="student.password"
                        @input="() => handleCheckClick(student.id)" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student-delete {
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
