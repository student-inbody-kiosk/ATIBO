<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailData from '@/components/admin/student/StudentDetailData.vue';

import router from '@/router';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { useRoute } from 'vue-router';
import { ref, onBeforeMount } from 'vue';

import type { Ref } from 'vue';
import type { StudentDetail } from '@/types/students.interface';

const route = useRoute();
const students: Ref<StudentDetail[]> = ref([]);
const deleteList: Ref<{ [index: string]: number }> = ref({});
const { fetchData: getStudents, isLoading: isGetStudentLoading } = useAxios(
    null,
    services.getStudents
);
const { fetchData: deleteTheStudent, isLoading: isDeleteStudentLoading } =
    useAxios(null, services.deleteStudents);

onBeforeMount(() => {
    const { grade, room, number, name } = route.query;
    getStudents(Number(grade), Number(room), Number(number), String(name)).then(
        (res) => (students.value = res)
    );
});

const handleCheckClick = function selectStudent(studentId: string) {
    if (studentId in deleteList.value) {
        return delete deleteList.value[studentId];
    }
    deleteList.value[studentId] = 1;
};

const handleDeleteClick = function deleteStudent() {
    if (!confirm('정말 삭제하시겠습니까? 되돌릴 수 없습니다')) return;
    const ids = Object.keys(deleteList.value);
    deleteTheStudent(ids).then(() => {
        router.push({ name: 'admin-student', query: route.query });
    });
};
</script>

<template>
    <VLoading
        v-if="isGetStudentLoading || isDeleteStudentLoading"
        color="admin-primary" />

    <div v-else class="admin-student-delete">
        <div class="admin-student-delete__header">학생 삭제</div>

        <div class="admin-student-delete__buttons">
            <VButton
                text="취소"
                color="gray"
                @click="
                    router.push({ name: 'admin-student', query: route.query })
                " />
            <VButton text="삭제" color="red" @click="handleDeleteClick" />
        </div>

        <section class="admin-student-list">
            <table>
                <caption>
                    Student Detail Table
                </caption>
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

.admin-student-delete__header {
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 1rem;
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
