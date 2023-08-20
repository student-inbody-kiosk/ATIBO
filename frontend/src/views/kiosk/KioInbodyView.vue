<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import KioInputGuide from '@/components/kiosk/KioInputGuide.vue';
import KioStudentForm from '@/components/kiosk/KioStudentForm.vue';
import KioLoginForm from '@/components/kiosk/inbody/KioLoginForm.vue';
import type { HeaderUpdate } from '@/types/app.interface';
import type { StudentSimple } from '@/types/students.interface';
import { useMeta } from 'vue-meta';

useMeta({
    title: 'ATIBO 아티보 인바디 키오스크',
    description: 'ATIBO 아티보 학생 인바디 정보 조회 키오스크',
});

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// Update kio-header
onBeforeMount(() => {
    emit('update-header', {
        title: '인바디 로그인',
        routeName: 'kiosk-index',
        routeParams: {},
        routeQuery: {},
    });
});

/* Check student data and change the component */
const student = ref<StudentSimple | null>(null);

// Update student info
const handleUpdateStudent = function openKioLoginForm(
    value: StudentSimple | null
) {
    student.value = value;
};
</script>

<template>
    <div class="kiosk-inbody-view">
        <KioInputGuide>
            <p v-if="!student">
                학년, 반, 번호를 입력해주세요 <br />
                (예시) 1학년 1반 1번 -> 10101
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
    grid-template-rows: 1fr auto;
    row-gap: 4rem;
    height: 100%;
    width: 100%;
    padding: 1rem 2rem;
}
</style>
