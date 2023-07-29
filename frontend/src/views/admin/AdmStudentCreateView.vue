<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentDataInput from '@/components/admin/StudentDataInput.vue';
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
        <div class="admin-student__header">학생 등록</div>
        <section class="admin-student-create">
            <TheButton
                text="+ 추가"
                color="green"
                size="md"
                emitMessage="submit"
                @submit="handleAddClick" />
            <TheButton
                text="등록"
                color="admin-primary"
                size="md"
                emitMessage="submit"
                @submit="handleCreateClick" />
        </section>

        <section class="admin-student-create-list">
            <table class="admin-student-create-list__table">
                <StudentDataLabel
                    class="admin-student-create-list__table__head" />
                <tbody>
                    <StudentDataInput
                        v-for="(student, index) in students"
                        :key="index"
                        :index="index"
                        :student="student"
                        @update-input="handleInput"
                        @delete-student="handleDeleteClick" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student-create-list {
    height: 36rem;
    overflow: auto;
}

.admin-student-create-list__table {
    width: 100%;
}
.admin-student-create-list__table__head {
    tr,
    th {
        @include z-index(label);
        position: sticky;
        top: 0;
    }
}
</style>
