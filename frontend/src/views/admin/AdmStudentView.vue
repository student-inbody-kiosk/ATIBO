<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import StudentSearchbar from '@/components/admin/student/StudentSearchbar.vue';
import StudentDetailDataLabel from '@/components/admin/student/StudentDetailDataLabel.vue';
import StudentDetailData from '@/components/admin/student/StudentDetailData.vue';
import VLoading from '@/components/common/VLoading.vue';

import { ref, onMounted } from 'vue';
import { toastTopErrorMessage } from '@/utils/toastManager';
import { useQueryStore } from '@/stores/query.store';
import { checkSearchInput } from '@/utils/checkInput';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';

import type { Ref } from 'vue';
import type { StudentDetail } from '@/types/students.interface';

const { fetchData: getStudents, isLoading } = useAxios(
    null,
    services.getStudents
);
const queryStore = useQueryStore();

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const students: Ref<StudentDetail[]> = ref([]);

onMounted(() => {
    const { grade: g, room: r, number: num, name: n } = queryStore.routeQuery;
    if (!g && !r && !num && !n) return;
    grade.value = g ? String(g) : '';
    room.value = r ? String(r) : '';
    number.value = num ? String(num) : '';
    name.value = n ? n : '';
    getStudents(g, r, num, n).then((res) => {
        students.value = res;
    });
});

const handleSubmit = function searchStudents() {
    if (!grade.value && !room.value && !name.value && !number.value) {
        toastTopErrorMessage('검색 조건을 입력해주세요.');
        return;
    }

    const data = {
        grade: grade.value,
        room: room.value,
        name: name.value,
        number: number.value,
    };
    if (checkSearchInput(data)) return;

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
            students.value = res;
        }
    );
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <div v-else class="admin-student">
        <div class="admin-student__header">
            <VButton
                text="뒤로"
                color="gray"
                @click="$router.push({ name: 'admin-main' })" />
            <div>학생 관리</div>
        </div>
        <StudentSearchbar
            :grade="grade"
            :room="room"
            :number="number"
            :name="name"
            :isStudentData="Boolean(students.length)"
            @grade="(value) => (grade = value)"
            @room="(value) => (room = value)"
            @number="(value) => (number = value)"
            @name="(value) => (name = value)"
            @search="handleSubmit"
            @enter="handleSubmit" />

        <section class="admin-student-list">
            <table>
                <caption>
                    Student Table
                </caption>
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
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: auto minmax(0, 1fr);
    padding-bottom: 1rem;

    div {
        font-size: 1.4rem;
        font-weight: 600;
        text-align: center;
    }
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
