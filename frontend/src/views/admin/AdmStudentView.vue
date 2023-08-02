<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import StudentDataLabel from '@/components/admin/StudentDataLabel.vue';
import StudentData from '@/components/admin/StudentData.vue';

import { ref, computed } from 'vue';

import { getStudents } from '@/apis/services/students';

import type { Ref } from 'vue';
// import type { Student } from '@/types/students.interface';

const grade = ref('');
const room = ref('');
const name = ref('');
const number = ref('');
const students: Ref<Student[]> = ref([]);
const query = computed(() => {
    return {
        grade: grade.value,
        room: room.value,
        number: number.value,
        name: name.value,
    };
});

const handleSubmit = function searchStudents() {
    // TODO: check regular expression
    getStudents(
        parseInt(grade.value),
        parseInt(room.value),
        parseInt(number.value),
        name.value
    ).then((res) => {
        students.value = res?.data;
    });
};
</script>

<template>
    <div class="admin-student">
        <div class="admin-student__header">학생 관리</div>
        <section class="admin-student__searchbar">
            <VInput
                id="grade"
                label="학년"
                refer="grade"
                :value="grade"
                @input="(value) => (grade = value)" />
            <VInput
                id="room"
                label="반"
                refer="room"
                :value="room"
                @input="(value) => (room = value)" />
            <VInput
                id="number"
                label="번호"
                refer="number"
                :value="number"
                @input="(value) => (number = value)" />
            <VInput
                id="name"
                label="이름"
                refer="name"
                :value="name"
                @input="(value) => (name = value)" />
            <VButton
                text="조회"
                color="admin-primary"
                size="md"
                @click="handleSubmit" />
            <VButton
                text="학생 추가"
                color="green"
                size="md"
                @click="$router.push({ name: 'admin-student-create' })" />
            <VButton
                v-if="students.length"
                text="학생 수정"
                color="admin-primary"
                size="md"
                @click="
                    $router.push({ name: 'admin-student-update', query })
                " />
            <VButton
                v-if="students.length"
                text="학생 삭제"
                color="red"
                size="md"
                @click="
                    $router.push({ name: 'admin-student-delete', query })
                " />
        </section>
        <section class="admin-student-list">
            <table class="admin-student-list__table">
                <StudentDataLabel class="admin-student-list__table__head" />
                <tbody>
                    <StudentData
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
.admin-student__searchbar {
    display: flex;
}

.admin-student-list {
    height: 36rem;
    overflow: auto;
}

.admin-student-list__table {
    width: 100%;
}
.admin-student-list__table__head {
    tr,
    th {
        @include z-index(label);
        position: sticky;
        top: 0;
    }
}
</style>
