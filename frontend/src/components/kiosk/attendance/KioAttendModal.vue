<script setup lang="ts">
import { inject } from 'vue';
import type { StudentSimple } from '@/types/students.interface';
import services from '@/apis/services';
import VButton from '@/components/common/VButton.vue';

const props = defineProps<{
    studentInfo: StudentSimple;
}>();

const emit = defineEmits<{
    (e: 'close-modal'): void;
}>();

const toast = inject('toast');

const handleClickAttend = function () {
    services
        .createAttendacne(
            props.studentInfo.grade,
            props.studentInfo.room,
            props.studentInfo.number
        )
        .then(() => {
            toast('출석되었습니다', 'success', 2500);
            emit('close-modal');
        })
        .catch((err) => {
            toast(
                `${err.detail ? err.detail : '출석에 실패했습니다'}`,
                'error',
                2500
            );
            emit('close-modal');
        });
};
</script>

<template>
    <div class="kiosk-attend-modal">
        <div>
            <p class="kiosk-attend-modal__text">
                {{ studentInfo.grade }} 학년 {{ studentInfo.room }} 학년
                {{ studentInfo.number }} 번 <br />{{ studentInfo.name }}
            </p>
            <VButton
                text="출석하기"
                color="green"
                size="md"
                @click="handleClickAttend" />
        </div>
    </div>
</template>

<style lang="scss">
.kiosk-attend-modal {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: 1rem;
}

.kiosk-attend-modal__text {
    text-align: center;
    font-size: 1.5rem;
    font-weight: 600;
    line-height: 180%;
}
</style>
