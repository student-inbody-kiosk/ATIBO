<script setup lang="ts">
import type { StudentAttendance } from '@/types/attendance.interface';

defineProps<{
    students: StudentAttendance[];
}>();

defineEmits<{
    (e: 'click', student: StudentAttendance[], index: number): void;
}>();
</script>

<template>
    <table>
        <caption>
            Attendance Table
        </caption>
        <thead>
            <tr>
                <th v-for="index in 31" :key="index">
                    {{ index }}
                </th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(student, index) in students" :key="index">
                <td v-for="index in 31" :key="index">
                    <span>
                        {{
                            student.attendanceSet[index]
                                ? student.attendanceSet[index][0].time
                                : '-'
                        }}
                    </span>

                    <div class="tooltip" v-if="student.attendanceSet[index]">
                        <div
                            class="tooltip__content"
                            v-for="(attendance, idx) in student.attendanceSet[
                                index
                            ]"
                            :key="idx">
                            {{ attendance.time }}
                        </div>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<style lang="scss" scoped>
th,
td {
    min-width: 5rem;
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

td:hover {
    cursor: pointer;
    position: relative;
    background-color: $admin-tertiary;

    .tooltip {
        display: block;
        position: absolute;
        left: 100%;
        bottom: 0;
        color: $white;
    }
}

.tooltip {
    display: none;
    background-color: $admin-deep-primary;
}

.tooltip__content {
    text-align: center;
    padding: 0.2rem;
    min-width: 4rem;
}
</style>
