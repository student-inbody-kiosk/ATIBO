<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentDataInput from '@/components/admin/StudentDataInput.vue';
import { createStudents } from '@/apis/services/students';
import type { Student } from '@/types/students.interface';

import { ref } from 'vue';
import type { Ref } from 'vue';

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
    console.log(students.value[index][item]);
};

const handleDeleteClick = function deleteStudent(index: number) {
    if (students.value.length === 1) return;
    students.value = students.value.filter((student, idx) => {
        return idx !== index;
    });
};
</script>

<template>
    <div class="admin-student-create">
        <div class="admin-student__header">학생 등록</div>
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
            @submit="() => createStudents(students)" />

        <section>
            <table class="admin-student__table">
                <StudentDataLabel />
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

<style lang="scss">
.admin-student__table {
    width: 100%;
}
</style>
