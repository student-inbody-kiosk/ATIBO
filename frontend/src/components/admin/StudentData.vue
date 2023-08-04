<script setup lang="ts">
defineProps<{
    index: number;
    grade: number;
    room: number;
    number: number;
    name: string;
}>();
</script>

<template>
    <tr class="student-attend">
        <td class="student-attend__content">{{ index + 1 }}</td>
        <td class="student-attend__content">{{ grade }}</td>
        <td class="student-attend__content">{{ room }}</td>
        <td class="student-attend__content">{{ number }}</td>
        <td class="student-attend__content">{{ name }}</td>
    </tr>
</template>

<style lang="scss" scoped>
.student-attend__content {
    min-width: 3rem;
    margin-top: 0.2rem;
    padding: 0.2rem;
    height: auto;
    border: 0.1rem solid $admin-secondary;
    background-color: $white;
    text-align: center;
}
</style>
