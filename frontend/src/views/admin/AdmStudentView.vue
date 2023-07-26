<script setup lang="ts">
import TheInput from '@/components/common/TheInput.vue';
import TheButton from '@/components/common/TheButton.vue';

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
    <div>
        <h2>학생 관리</h2>
        <section>
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
    </div>
</template>

<style lang="scss"></style>
