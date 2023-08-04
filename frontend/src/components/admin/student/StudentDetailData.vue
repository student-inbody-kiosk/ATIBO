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

import VInput from '@/components/common/VInput.vue';
</script>

<template>
    <tr class="student-data">
        <td v-if="!isDelete" class="student-data__content">{{ id + 1 }}</td>
        <td v-if="isDelete" class="student-data__content">
            <VInput
                type="checkbox"
                refer="del"
                @update-input="$emit('update-input', id)" />
        </td>
        <td class="student-data__content">{{ grade }}</td>
        <td class="student-data__content">{{ room }}</td>
        <td class="student-data__content">{{ number }}</td>
        <td class="student-data__content">{{ name }}</td>
        <td class="student-data__content">{{ sex }}</td>
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
