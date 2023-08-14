<script setup lang="ts">
import services from '@/apis/services';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import { useAxios } from '@/hooks/useAxios';
import type { StudentSimple } from '@/types/students.interface';

const props = defineProps<{
    student: StudentSimple;
}>();

const emit = defineEmits<{
    (e: 'close-modal'): void;
}>();

/* Create attendance asynchronously on submit */
const { fetchData: createAttendacne, isLoading } = useAxios<null>(
    null,
    services.createAttendacne
);

const handleSubmit = function onSubmitCreateAttendance() {
    createAttendacne(
        props.student.grade,
        props.student.room,
        props.student.number
    )
        .then(() => {
            emit('close-modal');
        })
        .catch(() => {
            emit('close-modal');
        });
};
</script>

<template>
    <VLoading v-if="isLoading" color="kiosk-primary" class="kiosk-loading" />
    <form class="kiosk-attend-modal" @submit.prevent="handleSubmit">
        <p class="kiosk-attend-modal__text">
            {{ student.grade }}학년 {{ student.room }}학년
            {{ student.number }}번 <br />{{ student.name }}
        </p>
        <VButton text="출석하기" type="submit" color="green" size="lg" />
    </form>
</template>

<style lang="scss">
.kiosk-attend-modal {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    gap: 1.5rem;
    margin: 0 3rem;
}

.kiosk-attend-modal__text {
    text-align: center;
    font-size: 2rem;
    font-weight: 600;
    line-height: 180%;
}
</style>
