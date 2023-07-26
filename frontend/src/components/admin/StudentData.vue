<script setup lang="ts">
defineProps<{
    id: number;
    grade: number;
    room: number;
    number: number;
    name: string;
    sex: string;
    birthDate: string;
    password: string;
}>();
</script>

<template>
    <tbody>
        <tr class="student-data">
            <td class="student-data__content">{{ id }}</td>
            <td class="student-data__content">{{ grade }}</td>
            <td class="student-data__content">{{ room }}</td>
            <td class="student-data__content">{{ number }}</td>
            <td class="student-data__content">{{ name }}</td>
            <td class="student-data__content">{{ sex }}</td>
            <td class="student-data__content">{{ birthDate }}</td>
            <td class="student-data__content">{{ password }}</td>
        </tr>
    </tbody>
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
