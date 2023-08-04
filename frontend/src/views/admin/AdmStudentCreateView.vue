<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/StudentDetailDataLabel.vue';
import StudentDetailInput from '@/components/admin/StudentDetailInput.vue';
import { createStudents } from '@/apis/services/students';
import type { Student } from '@/types/admin.interface';

import { ref } from 'vue';
import type { Ref } from 'vue';
import router from '@/router';

const students: Ref<Student[]> = ref([
    {
        grade: '',
        room: '',
        number: '',
        name: '',
        sex: 1,
        birthDate: '',
        password: '',
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
        password: '',
    });
};

const handleInput = function updateStudentData<T extends keyof Student>(
    index: number,
    item: T,
    data: Student[T]
) {
    students.value[index][item] = data;
};

const handleDeleteClick = function deleteStudent(index: number) {
    if (students.value.length === 1) return;
    students.value = students.value.filter((student, idx) => {
        return idx !== index;
    });
};

const handleCreateClick = function createStudent() {
    createStudents(students.value).then(() => {
        router.push({ name: 'admin-student' });
    });
};
</script>

<template>
    <div class="admin-student-create">
        <div>학생 등록</div>
        <section class="admin-student-create__buttons">
            <VButton text="+ 추가" color="green" @click="handleAddClick" />
            <VButton
                text="등록"
                color="admin-primary"
                @click="handleCreateClick" />
        </section>

        <section class="admin-student-create-list">
            <table class="admin-student-create-list__table">
                <StudentDetailDataLabel
                    class="admin-student-create-list__table__head" />
                <tbody>
                    <StudentDetailInput
                        v-for="(student, index) in students"
                        :key="index"
                        :index="index"
                        :student="student"
                        :isCreate="true"
                        @update-input="handleInput"
                        @delete-student="handleDeleteClick" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student-create {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-create__buttons {
    display: flex;
    justify-content: flex-end;
}
.admin-student-create-list {
    overflow: auto;
}

.admin-student-create-list__table {
    width: 100%;
}
</style>
