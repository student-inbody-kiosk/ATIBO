<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailInput from '@/components/admin/student/StudentDetailInput.vue';
import VLoading from '@/components/common/VLoading.vue';

import { checkStudentInput } from '@/utils/checkInput';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { ref, onMounted } from 'vue';
import router from '@/router';
import { useRoute } from 'vue-router';

import type { Ref } from 'vue';
import type { StudentDetail } from '@/types/students.interface';

const route = useRoute();
const students: Ref<StudentDetail[]> = ref([]);
const updateIndexSet: Ref<Set<number>> = ref(new Set<number>());
const { fetchData: getStudents, isLoading: isGetStudentLoading } = useAxios(
    null,
    services.getStudents
);
const { fetchData: updateStudents, isLoading: isUpdateStudentLoading } =
    useAxios(null, services.updateStudents);

onMounted(() => {
    const { grade, room, number, name } = route.query;
    getStudents(Number(grade), Number(room), Number(number), String(name)).then(
        (res) => (students.value = res)
    );
});

const handleInput = function updateStudentData<T extends keyof StudentDetail>(
    index: number,
    item: T,
    data: StudentDetail[T]
) {
    students.value[index][item] = data;

    // 수정된 학생의 인덱스 updateIndexSet에 반영
    updateIndexSet.value.add(index);
};

const errorIndex = ref();
const handleUpdateClick = function updateStudent() {
    const updateStudentList = [];

    // updateList의 인덱스 기준으로 정규식 검사 및 수정된 학생 리스트 생성
    for (const index of updateIndexSet.value) {
        const errorStudentIndex = checkStudentInput(
            students.value[index],
            index
        );
        if (errorStudentIndex !== false) {
            errorIndex.value = errorStudentIndex;
            return;
        }
        updateStudentList.push(students.value[index]);
    }

    updateStudents(updateStudentList).then(() => {
        router.push({ name: 'admin-student', query: route.query });
    });
};
</script>

<template>
    <VLoading
        v-if="isGetStudentLoading || isUpdateStudentLoading"
        color="admin-primary" />

    <div v-else class="admin-student-update">
        <div class="admin-student-update__header">학생 수정</div>
        <section class="admin-student-update__buttons">
            <VButton
                text="취소"
                color="gray"
                @click="() => router.push({ name: 'admin-student' })" />
            <VButton text="완료" color="green" @click="handleUpdateClick" />
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
                        :errorIndex="errorIndex"
                        @update-input="handleInput" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student-update {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-update__header {
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 1rem;
}

.admin-student-update__buttons {
    display: flex;
    justify-content: space-between;
    gap: 0.5rem;
    padding-bottom: 0.5rem;
}
.admin-student-update-list {
    overflow: auto;
    margin: 0 auto;
    width: 100%;

    table {
        width: 100%;
    }
}
</style>
