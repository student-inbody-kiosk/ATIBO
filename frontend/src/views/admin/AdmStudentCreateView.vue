<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentDataInput from '@/components/admin/StudentDataInput.vue';

import { ref } from 'vue';
import type { Ref } from 'vue';

interface Student {
    grade: string;
    room: string;
    number: string;
    name: string;
    sex: number;
    birthDate: string;
    password: string;
}

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

const handleClick = function addStudent() {
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
</script>

<template>
    <div class="admin-student-create">
        <div class="admin-student__header">학생 등록</div>
        <TheButton
            text="+ 추가"
            color="green"
            size="md"
            emitMessage="submit"
            @submit="handleClick" />
        <TheButton
            text="등록"
            color="admin-primary"
            size="md"
            emitMessage="submit"
            @submit="() => console.log('등록')" />

        <section>
            <table class="admin-student__table">
                <StudentDataLabel />
                <tbody>
                    <StudentDataInput
                        v-for="(student, index) in students"
                        :key="index"
                        :index="index"
                        :student="student"
                        @update-input="handleInput" />
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
