<script setup lang="ts">
const props = defineProps<{
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
}>();

import TheInput from '@/components/common/TheInput.vue';
import IconButton from '../common/IconButton.vue';

import { ref } from 'vue';

const sexData = ref(props.student.sex);
</script>

<template>
    <tr>
        <td class="student-data-input__content">
            <IconButton
                emitMessage="delete"
                @delete="() => $emit('delete-student', index)">
                <template #icon>
                    <font-awesome-icon icon="circle-minus" color="red" />
                </template>
            </IconButton>
        </td>
        <td class="student-data-input__content">
            <TheInput
                type="text"
                refer="grade"
                :value="student.grade"
                @input="
                    (e) => $emit('update-input', index, 'grade', e.target.value)
                " />
        </td>
        <td class="student-data-input__content">
            <TheInput
                type="text"
                refer="room"
                :value="student.room"
                @input="
                    (e) => $emit('update-input', index, 'room', e.target.value)
                " />
        </td>
        <td class="student-data-input__content">
            <TheInput
                type="text"
                refer="number"
                :value="student.number"
                @input="
                    (e) =>
                        $emit('update-input', index, 'number', e.target.value)
                " />
        </td>
        <td class="student-data-input__content">
            <TheInput
                type="text"
                refer="name"
                :value="student.name"
                @input="
                    (e) => $emit('update-input', index, 'name', e.target.value)
                " />
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
            <TheInput
                type="date"
                refer="birthDate"
                :value="student.birthDate"
                @input="
                    (e) =>
                        $emit(
                            'update-input',
                            index,
                            'birthDate',
                            e.target.value
                        )
                " />
        </td>
        <td class="student-data-input__content">
            <TheInput
                type="text"
                refer="password"
                :value="student.password"
                @input="
                    (e) =>
                        $emit('update-input', index, 'password', e.target.value)
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
</style>
