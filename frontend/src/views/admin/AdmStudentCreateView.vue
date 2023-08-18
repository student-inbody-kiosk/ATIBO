<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailInput from '@/components/admin/student/StudentDetailInput.vue';
import VLoading from '@/components/common/VLoading.vue';

import { ref } from 'vue';
import router from '@/router';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { checkStudentInput } from '@/utils/checkInput';

import type { StudentCreate } from '@/types/students.interface';

const { fetchData: createStudents, isLoading } = useAxios(
    null,
    services.createStudents
);

const students = ref<StudentCreate[]>([
    {
        grade: '',
        room: '',
        number: '',
        name: '',
        sex: 1,
        birthDate: '',
        password: '0000',
    },
]);

const handleAddClick = function addStudent() {
    students.value.push({
        grade: '',
        room: '',
        number: '',
        name: '',
        sex: 1,
        birthDate: '',
        password: '0000',
    });
};

const handleInput = function updateStudentData<T extends keyof StudentCreate>(
    index: number,
    item: T,
    data: StudentCreate[T]
) {
    students.value[index][item] = data;
};

const handleDeleteClick = function deleteStudent(index: number) {
    if (students.value.length === 1) return;
    students.value = students.value.filter((student, idx) => {
        return idx !== index;
    });
};

const errorIndex = ref();
const handleCreateClick = function createStudent() {
    // 정규식 검사
    for (let idx in students.value) {
        const errorStudentIndex = checkStudentInput(
            students.value[idx],
            Number(idx)
        );
        if (errorStudentIndex !== false) {
            errorIndex.value = errorStudentIndex;
            return;
        }
    }

    createStudents(students.value).then(() => {
        router.push({ name: 'admin-student' });
    });
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <div v-else class="admin-student-create">
        <div class="admin-student-create__header">학생 등록</div>
        <section class="admin-student-create__buttons">
            <VButton
                text="취소"
                color="gray"
                @click="$router.push({ name: 'admin-student' })" />
            <div>
                <VButton
                    text="+ 학생 추가"
                    color="green"
                    @click="handleAddClick" />
                <VButton
                    text="등록"
                    color="admin-primary"
                    @click="handleCreateClick" />
            </div>
        </section>

        <section class="admin-student-create-list">
            <table class="admin-student-create-list__table">
                <caption>
                    Student Create Table
                </caption>
                <StudentDetailDataLabel />
                <tbody>
                    <StudentDetailInput
                        v-for="(student, index) in students"
                        :key="index"
                        :index="index"
                        :student="student"
                        :isCreate="true"
                        :errorIndex="errorIndex"
                        @update-input="handleInput"
                        @delete-student="handleDeleteClick" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student-create {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-create__header {
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 1rem;
}

.admin-student-create__buttons {
    display: flex;
    justify-content: space-between;
    gap: 0.5rem;
    padding-bottom: 0.5rem;

    div {
        display: flex;
        gap: 0.5rem;
    }
}
.admin-student-create-list {
    overflow: auto;
}

.admin-student-create-list__table {
    width: 100%;
}
</style>
