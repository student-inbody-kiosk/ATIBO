<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailData from '@/components/admin/student/StudentDetailData.vue';

import { useRoute } from 'vue-router';
import { ref, onBeforeMount } from 'vue';

import { getStudents, deleteStudents } from '@/apis/services/students';

import type { Ref } from 'vue';
import type { StudentDetail } from '@/types/students.interface';
import router from '@/router';

const route = useRoute();
const students: Ref<StudentDetail[]> = ref([]);
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

        <div class="admin-student-delete__buttons">
            <VButton
                text="취소"
                color="admin-primary"
                @click="
                    router.push({ name: 'admin-student', query: route.query })
                " />
            <VButton text="삭제" color="red" @click="handleDeleteClick" />
        </div>

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
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-delete__buttons {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0.5rem 0;
}

.admin-student-list {
    overflow: auto;
}

table {
    width: 100%;
}
</style>
