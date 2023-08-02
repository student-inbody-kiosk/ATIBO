<script setup lang="ts">
import { inject } from 'vue';
import type { StudentSimple } from '@/types/students.interface';
import services from '@/apis/services';
import VButton from '@/components/common/VButton.vue';

const props = defineProps<{
    student: StudentSimple;
}>();

const emit = defineEmits<{
    (e: 'close-modal'): void;
}>();

const toast = inject('toast');

const handleClick = function createAttendance() {
    services
        .createAttendacne(
            props.student.grade,
            props.student.room,
            props.student.number
        )
        .then(() => {
            toast('출석되었습니다', 'success', 2500, 'center', 'lg');
            emit('close-modal');
        })
        .catch((err) => {
            const message = err.response.data.detail
                ? err.response.data.detail
                : '출석에 실패했습니다';
            toast(message, 'error', 2500, 'center', 'lg');
            emit('close-modal');
        });
};
</script>

<template>
    <div class="kiosk-attend-modal">
        <p class="kiosk-attend-modal__text">
            {{ student.grade }}학년 {{ student.room }}학년
            {{ student.number }}번 <br />{{ student.name }}
        </p>
        <VButton text="출석하기" color="green" size="md" @click="handleClick" />
    </div>
</template>

<style lang="scss">
.kiosk-attend-modal {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: 1rem;
    margin: 0 2rem;
}

.kiosk-attend-modal__text {
    text-align: center;
    font-size: 1.5rem;
    font-weight: 600;
    line-height: 180%;
}
</style>
