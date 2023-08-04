<script setup lang="ts">
import type { Attendance } from '@/types/attendance.interface';

defineProps<{
    students: Attendance[];
}>();
</script>

<template>
    <table>
        <thead>
            <tr>
                <th v-for="index in 31" :key="index">
                    {{ index }}
                </th>
            </tr>
        </thead>
        <tbody class="hi">
            <tr v-for="(student, index) in students" :key="index">
                <td v-for="index in 31" :key="index">-</td>
            </tr>
        </tbody>
    </table>
</template>

<style lang="scss" scoped>
// thead {
//     @include z-index(label);
//     position: sticky;
//     top: 0;
// }

th,
td {
    min-width: 3rem;
    margin-top: 0.2rem;
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    text-align: center;
}

th {
    background-color: $admin-tertiary;
}

td {
    background-color: $white;
}
</style>
