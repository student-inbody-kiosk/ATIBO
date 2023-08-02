<script setup lang="ts">
import KioInputGuide from '@/components/kiosk/KioInputGuide.vue';
import KioStudentForm from '@/components/kiosk/KioStudentForm.vue';
import KioLoginForm from '@/components/kiosk/inbody/KioLoginForm.vue';
import type { StudentSimple } from '@/types/students.interface';
import { onBeforeMount, ref } from 'vue';
import type { Header } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: Header): void;
}>();

const student = ref<StudentSimple | null>(null);

const handleUpdateStudent = function (value: StudentSimple | null) {
    student.value = value;
};

onBeforeMount(() => {
    emit('update-header', { title: '출석 확인', routeName: 'kiosk-index' });
});
</script>

<template>
    <div class="kiosk-inbody-view">
        <KioInputGuide>
            <p v-if="!student">
                학년, 반, 번호를 입력해주세요 <br />
                예시 1학년 1반 1번 -> 10101
            </p>
            <p v-else>
                {{ student.grade }}학년 {{ student.room }}반
                {{ student.number }}번 {{ student.name }}
                <br />
                비밀번호를 입력해주세요
            </p>
        </KioInputGuide>
        <KioStudentForm v-if="!student" @update-student="handleUpdateStudent" />
        <KioLoginForm
            v-else
            :student="student"
            @update-student="handleUpdateStudent" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr fit-content;
    gap: 5rem;
    padding: 1rem 2rem;
    height: 100%;
}
</style>
