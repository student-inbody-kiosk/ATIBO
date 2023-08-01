<script setup lang="ts">
const props = withDefaults(
    defineProps<{
        index: number;
        student: {
            grade: string;
            room: string;
            number: string;
            name: string;
            sex: number;
            birthDate: string;
            password: string;
        };
        isCreate?: boolean;
    }>(),
    { isCreate: false }
);

import VInput from '@/components/common/VInput.vue';
import VIconButton from '../common/VIconButton.vue';

import { ref } from 'vue';

const sexData = ref(props.student.sex);
</script>

<template>
    <tr>
        <td class="student-data-input__content">
            <span v-if="!isCreate">{{ index + 1 }}</span>
            <VIconButton
                v-if="isCreate"
                @click="$emit('delete-student', index)">
                <font-awesome-icon icon="circle-minus" color="red" />
            </VIconButton>
        </td>
        <td class="student-data-input__content">
            <VInput
                id="grade"
                refer="grade"
                :value="student.grade"
                @input="
                    (grade) => $emit('update-input', index, 'grade', grade)
                " />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="room"
                refer="room"
                :value="student.room"
                @input="(room) => $emit('update-input', index, 'room', room)" />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="number"
                refer="number"
                :value="student.number"
                @input="
                    (number) => $emit('update-input', index, 'number', number)
                " />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="name"
                refer="name"
                :value="student.name"
                @input="(name) => $emit('update-input', index, 'name', name)" />
        </td>
        <td class="student-data-input__content">
            <select
                v-model="sexData"
                :name="'sex'"
                @change="(e) => $emit('update-input', index, 'sex', sexData)">
                <option value="1">남자</option>
                <option value="2">여자</option>
                <option value="0">알 수 없음</option>
                <option value="9">기타</option>
            </select>
        </td>
        <td class="student-data-input__content">
            <VInput
                id="date"
                type="date"
                refer="birthDate"
                :value="student.birthDate"
                @input="
                    (date) => $emit('update-input', index, 'birthDate', date)
                " />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="password"
                refer="password"
                :value="student.password"
                @input="
                    (password) =>
                        $emit('update-input', index, 'password', password)
                " />
        </td>
    </tr>
</template>

<style lang="scss" scoped>
.student-data-input__content {
    margin-top: 0.2rem;
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    background-color: $white;
    text-align: center;
}

input {
    text-align: center;
}
</style>
