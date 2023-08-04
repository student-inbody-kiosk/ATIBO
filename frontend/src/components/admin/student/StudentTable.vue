<script setup lang="ts">
import StudentDataLabel from '@/components/admin/student/StudentDataLabel.vue';
import StudentData from '@/components/admin/student/StudentData.vue';
import type { Attendance } from '@/types/attendance.interface';

defineProps<{
    students: Attendance[];
}>();
</script>

<template>
    <table class="student-table">
        <StudentDataLabel />
        <tbody>
            <StudentData
                v-for="(student, index) in students"
                :key="student.name"
                :index="index"
                :grade="student.grade"
                :room="student.room"
                :number="student.room"
                :name="student.name" />
        </tbody>
    </table>
</template>

<style lang="scss"></style>
