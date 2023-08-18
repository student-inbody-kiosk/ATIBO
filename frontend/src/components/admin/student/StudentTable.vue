<script setup lang="ts">
import StudentDataLabel from '@/components/admin/student/StudentDataLabel.vue';
import StudentData from '@/components/admin/student/StudentData.vue';
import type { StudentAttendance } from '@/types/attendance.interface';
import type { StudentSimple } from '@/types/students.interface';

defineProps<{
    students: StudentAttendance[];
}>();

defineEmits<{
    (e: 'click', student: StudentSimple): void;
}>();
</script>

<template>
    <table class="student-table">
        <caption>
            Student Table
        </caption>
        <StudentDataLabel />
        <tbody>
            <StudentData
                v-for="(student, index) in students"
                :key="student.id"
                :index="index"
                :grade="student.grade"
                :room="student.room"
                :number="student.number"
                :name="student.name"
                @click="$emit('click', student)" />
        </tbody>
    </table>
</template>

<style lang="scss" scoped>
table {
    height: fit-content;
}
</style>
