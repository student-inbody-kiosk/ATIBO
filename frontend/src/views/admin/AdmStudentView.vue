<script setup lang="ts">
import StudentSearchBar from '@/components/admin/student/StudentSearchBar.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailData from '@/components/admin/student/StudentDetailData.vue';
import { ref, onMounted } from 'vue';
import { getStudents } from '@/apis/services/students';
import { toastTopErrorMessage } from '@/utils/toastManager';
import type { Ref } from 'vue';
import type { StudentDetail } from '@/types/students.interface';
import { useQueryStore } from '@/stores/query.store';

const queryStore = useQueryStore();

onMounted(() => {
    const { grade, room, number, name } = queryStore.routeQuery;
    if (!grade && !room && !number && !name) return;
    getStudents(grade, room, number, name).then((res) => {
        students.value = res?.data;
    });
});

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const students: Ref<StudentDetail[]> = ref([]);

const handleSubmit = function searchStudents() {
    if (!grade.value && !room.value && !name.value && !number.value) {
        toastTopErrorMessage('검색 조건을 입력해주세요.');
        return;
    }

    const parsedGrade = parseInt(grade.value);
    const parsedRoom = parseInt(room.value);
    const parsedNumber = parseInt(number.value);

    queryStore.updateQuery({
        grade: parsedGrade,
        room: parsedRoom,
        number: parsedNumber,
        name: name.value,
    });

    getStudents(parsedGrade, parsedRoom, parsedNumber, name.value).then(
        (res) => {
            students.value = res?.data;
        }
    );
};
</script>

<template>
    <div class="admin-student">
        <div class="admin-student__header">학생 관리</div>
        <StudentSearchBar
            :grade="grade"
            :room="room"
            :number="number"
            :name="name"
            :isStudentData="Boolean(students.length)"
            @grade="(value) => (grade = value)"
            @room="(value) => (room = value)"
            @number="(value) => (number = value)"
            @name="(value) => (name = value)"
            @search="handleSubmit" />

        <section class="admin-student-list">
            <table>
                <StudentDetailDataLabel />
                <tbody>
                    <StudentDetailData
                        v-for="(data, index) in students"
                        :key="data.id"
                        :id="index"
                        :grade="data.grade"
                        :room="data.room"
                        :number="data.number"
                        :name="data.name"
                        :sex="data.sex"
                        :birthDate="data.birthDate"
                        :password="data.password" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-student__header {
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 1rem;
}
.admin-student {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-student-list {
    overflow: auto;
}

table {
    width: 100%;
}
</style>
