<script setup lang="ts">
import TheInput from '@/components/common/TheInput.vue';
import TheButton from '@/components/common/TheButton.vue';
import StudentLabel from '@/components/admin/StudentLabel.vue';

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
        </section>
        <section>
            <table class="admin-student__table">
                <StudentLabel />
            </table>
        </section>
    </div>
</template>

<style lang="scss">
.admin-student__searchbar {
    display: flex;
}

.admin-student__table {
    background-color: $gray-opacity;
    width: 100%;
}
</style>
