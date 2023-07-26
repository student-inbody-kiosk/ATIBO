<script setup lang="ts">
import TheInput from '@/components/common/TheInput.vue';
import TheButton from '@/components/common/TheButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentData from '@/components/admin/StudentData.vue';

import { ref } from 'vue';

import { getStudents } from '@/apis/services/students';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');

const handleSubmit = function searchStudents() {
    // TODO: check regular expression
    getStudents(
        parseInt(grade.value),
        parseInt(room.value),
        parseInt(number.value),
        name.value
    );

    console.log(grade.value, room.value, number.value, name.value);
};

const dummy = [
    {
        id: '4d9f1679-9c87-4ece-8f90-863002780b4b',
        name: '이병호',
        grade: 1,
        room: 1,
        number: 1,
        sex: '남',
        password: '0000',
        birthDate: '2023-07-24',
    },
    {
        id: '4d9f1679-9c87-4ece-8f90-863002780b4b',
        name: '정예지',
        grade: 1,
        room: 1,
        number: 1,
        sex: '여',
        password: '0000',
        birthDate: '2023-04-01',
    },
    {
        id: '4d9f1679-9c87-4ece-8f90-863002780b4b',
        name: '정예지',
        grade: 1,
        room: 1,
        number: 1,
        sex: '여',
        password: '0000',
        birthDate: '2023-04-01',
    },
    {
        id: '4d9f1679-9c87-4ece-8f90-863002780b4b',
        name: '정예지',
        grade: 1,
        room: 1,
        number: 1,
        sex: '여',
        password: '0000',
        birthDate: '2023-04-01',
    },
];
</script>

<template>
    <div class="admin-student">
        <div class="admin-student__header">학생 관리</div>
        <section class="admin-student__searchbar">
            <TheInput
                label="학년"
                type="text"
                refer="grade"
                :value="grade"
                @update-input="(value) => (grade = value)" />
            <TheInput
                label="반"
                type="text"
                refer="room"
                :value="room"
                @update-input="(value) => (room = value)" />
            <TheInput
                label="번호"
                type="text"
                refer="number"
                :value="number"
                @update-input="(value) => (number = value)" />
            <TheInput
                label="이름"
                type="text"
                refer="name"
                :value="name"
                @update-input="(value) => (name = value)" />
            <TheButton
                text="조회"
                color="admin-primary"
                size="md"
                emitMessage="submit"
                @submit="handleSubmit" />
            <TheButton
                text="+ 학생 추가"
                color="green"
                size="md"
                emitMessage="go-create"
                @go-create="$router.push({ name: 'admin-student-create' })" />
        </section>
        <section>
            <table class="admin-student__table">
                <StudentDataLabel />
                <StudentData
                    v-for="(data, index) in dummy"
                    :key="data.id"
                    :id="index + 1"
                    :grade="data.grade"
                    :room="data.room"
                    :number="data.number"
                    :name="data.name"
                    :sex="data.sex"
                    :birthDate="data.birthDate"
                    :password="data.password" />
            </table>
        </section>
    </div>
</template>

<style lang="scss">
.admin-student__searchbar {
    display: flex;
}

.admin-student__table {
    width: 100%;
}
</style>
