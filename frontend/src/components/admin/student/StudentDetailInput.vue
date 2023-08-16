<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VIconButton from '@/components/common/VIconButton.vue';
import type { StudentDetail } from '@/types/students.interface';
import { ref } from 'vue';
const props = withDefaults(
    defineProps<{
        index: number;
        student: StudentDetail;
        errorIndex?: number;
        isCreate?: boolean;
    }>(),
    { isCreate: false }
);

const sexData = ref(props.student.sex);
const maxDate = new Date().toISOString().split('T')[0];
</script>

<template>
    <tr :class="errorIndex === index ? 'is-error' : null">
        <td class="student-data-input__content">
            <VIconButton
                v-if="isCreate"
                @click="$emit('delete-student', index)">
                <font-awesome-icon icon="circle-minus" color="red" />
            </VIconButton>
            <span v-else>{{ index + 1 }}</span>
        </td>
        <td class="student-data-input__content">
            <VInput
                id="grade"
                type="number"
                :min="1"
                :value="student.grade"
                @input="
                    (grade) => $emit('update-input', index, 'grade', grade)
                " />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="room"
                type="number"
                :min="1"
                :value="student.room"
                @input="(room) => $emit('update-input', index, 'room', room)" />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="number"
                type="number"
                :min="1"
                :value="student.number"
                @input="
                    (number) => $emit('update-input', index, 'number', number)
                " />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="name"
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
                :value="student.birthDate"
                :max="maxDate"
                @input="
                    (date) => $emit('update-input', index, 'birthDate', date)
                " />
        </td>
        <td class="student-data-input__content">
            <VInput
                id="password"
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

.is-error {
    outline: 0.1rem solid $red;
}
</style>
