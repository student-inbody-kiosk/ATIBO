<script setup lang="ts">
withDefaults(
    defineProps<{
        id: number;
        grade: number;
        room: number;
        number: number;
        name: string;
        sex: number;
        birthDate: string;
        password: string;
        isDelete?: boolean;
    }>(),
    { isDelete: false }
);

defineEmits<{
    (e: 'input'): void;
}>();

import VInput from '@/components/common/VInput.vue';

const convertSex = {
    0: '알 수 없음',
    1: '남자',
    2: '여자',
    9: '그 외',
};
</script>

<template>
    <tr class="student-data">
        <td class="student-data__content">
            <VInput
                v-if="isDelete"
                id="delete"
                type="checkbox"
                @input="$emit('input')" />
            <span v-else>{{ id + 1 }}</span>
        </td>
        <td class="student-data__content">{{ grade }}</td>
        <td class="student-data__content">{{ room }}</td>
        <td class="student-data__content">{{ number }}</td>
        <td class="student-data__content">{{ name }}</td>
        <td class="student-data__content">{{ convertSex[sex] }}</td>
        <td class="student-data__content">{{ birthDate }}</td>
        <td class="student-data__content">{{ password }}</td>
    </tr>
</template>

<style lang="scss" scoped>
.student-data__content {
    margin-top: 0.2rem;
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    background-color: $white;
    text-align: center;
}
</style>
