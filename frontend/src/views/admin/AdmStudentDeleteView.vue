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
                        v-for="(data, index) in students"
                        :isDelete="true"
                        :key="data.id"
                        :id="index"
                        :grade="data.grade"
                        :room="data.room"
                        :number="data.number"
                        :name="data.name"
                        :sex="data.sex"
                        :birthDate="data.birthDate"
                        :password="data.password"
                        @update-input="(index) => handleCheckClick(data.id)" />
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
