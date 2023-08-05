<script setup lang="ts">
import type { Inbody } from '@/types/inbody.interace';

defineProps<{
    days: number;
    students: Inbody[];
    inbodyList: string[][];
}>();
</script>

<template>
    <table>
        <thead>
            <tr>
                <th :colspan="days">인바디 기록</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(student, i) in students" :key="i">
                <td
                    v-for="j in days"
                    :key="j"
                    @click="$emit('click', i, j - 1)">
                    {{ inbodyList[i][j - 1] }}
                </td>
            </tr>
        </tbody>
    </table>
</template>

<style lang="scss" scoped>
table {
    overflow-x: auto;
    height: fit-content;
}

tr,
th {
    width: 42rem;
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    background-color: $admin-tertiary;
    text-align: left;
}

td {
    min-width: 6rem;
    margin-top: 0.2rem;
    padding: 0.2rem;
    height: auto;
    border: 0.1rem solid $admin-secondary;
    background-color: $white;
    text-align: center;
}
</style>
