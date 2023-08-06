<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailInput from '@/components/admin/student/StudentDetailInput.vue';
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

const updateIndexSet: Ref<Set<number>> = ref(new Set<number>());

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

    // 수정된 학생의 인덱스 updateIndexSet에 반영
    updateIndexSet.value.add(index);
};

const handleUpdateClick = function updateStudent() {
    const updateStudentList = [];

    // updateList의 인덱스 기준으로 수정된 학생 리스트 생성
    for (const index of updateIndexSet.value) {
        updateStudentList.push(students.value[index]);
    }

    updateStudents(updateStudentList).then(() => {
        router.push({ name: 'admin-student', query: route.query });
    });
};
</script>

<template>
    <div class="admin-student-update">
        <div>학생 수정</div>
        <section class="admin-student-update__buttons">
            <VButton text="완료" color="green" @click="handleUpdateClick" />
            <VButton
                text="취소"
                color="red"
                @click="() => router.push({ name: 'admin-student' })" />
        </section>

        <section class="admin-student-update-list">
            <table>
                <StudentDetailDataLabel />
                <tbody>
                    <StudentDetailInput
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
.admin-student-update {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-update__buttons {
    display: flex;
    justify-content: flex-end;
}
.admin-student-update-list {
    overflow: auto;
}
</style>
