<script setup lang="ts">
import type { Inbody, InbodyDetail } from '@/types/inbody.interace';

defineProps<{
    days: number;
    students: Inbody[];
    inbodyList: InbodyDetail[];
}>();

defineEmits<{
    (e: 'click', i: number, j: number): void;
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
                <td v-for="j in days" :key="j">
                    <span
                        @click="
                            if (inbodyList[i][j - 1] != null) {
                                $emit('click', i, j - 1);
                            }
                        ">
                        {{
                            inbodyList[i][j - 1] != null
                                ? inbodyList[i][j - 1].testDate
                                : '-'
                        }}
                    </span>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<style lang="scss" scoped>
table {
    width: 100%;
    overflow-x: auto;
    height: fit-content;
}

tr,
th {
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
    background-color: $admin-tertiary;
}

td {
    min-width: 7rem;
    margin-top: 0.2rem;
    padding: 0.2rem;
    height: auto;
    border: 0.1rem solid $admin-secondary;
    background-color: $white;
    text-align: center;
}
</style>
