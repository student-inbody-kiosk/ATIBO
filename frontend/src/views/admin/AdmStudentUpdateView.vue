<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentDataInput from '@/components/admin/StudentDataInput.vue';
import type { Student } from '@/types/admin.interface';

import { getStudents, updateStudents } from '@/apis/services/students';

import { ref, onBeforeMount } from 'vue';
import type { Ref } from 'vue';
import router from '@/router';
import { useRoute } from 'vue-router';

const route = useRoute();
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
const updateList: Ref<Student[]> = ref([]);

onBeforeMount(() => {
    const { grade, room, number, name } = route.query;
    getStudents(Number(grade), Number(room), Number(number), String(name)).then(
        (res) => (students.value = res?.data)
    );
});

const handleInput = function updateStudentData<T extends keyof Student>(
    index: number,
    item: T,
    data: Student[T]
) {
    students.value[index][item] = data;

    // 수정된 학생의 인덱스 updateList에 반영
    if (index in updateList.value) return;
    updateList.value.push(index);
};

const handleUpdateClick = function updateStudent() {
    let studentList = [];

    // updateList의 인덱스 기준으로 수정된 학생 리스트 생성
    for (let i = 0; i < updateList.value.length; i++) {
        studentList.push(students.value[i]);
    }

    console.log(studentList);

    updateStudents(studentList).then(() => {
        router.push({ name: 'admin-student', query: route.query });
    });
};
</script>

<template>
    <div class="admin-student-create">
        <div class="admin-student__header">학생 수정</div>
        <section class="admin-student-create">
            <TheButton
                text="완료"
                color="green"
                size="md"
                emitMessage="submit"
                @submit="handleUpdateClick" />
            <TheButton
                text="취소"
                color="red"
                size="md"
                emitMessage="cancel"
                @cancel="() => router.push({ name: 'admin-student' })" />
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
                        @update-input="handleInput" />
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
