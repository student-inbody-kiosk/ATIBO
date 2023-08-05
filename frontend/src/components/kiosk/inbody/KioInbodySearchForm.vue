<script setup lang="ts">
import { useRouter } from 'vue-router';
import regexes from '@/constants/regexes';
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';

const props = defineProps<{
    grade: number;
    room: number;
    number: number;
    startDate: string;
    endDate: string;
}>();

/* Navigate according to the submitted date input value */
const router = useRouter();

const handleSubmit = function getTheStudentInbodys(event: Event) {
    const form = event.target as HTMLFormElement;
    if (!form) return;

    const formData = new FormData(form);
    const startDate = (formData.get('startDate') as string) || '';
    const endDate = (formData.get('endDate') as string) || '';
    if (!regexes.date.test(startDate) || !regexes.date.test(endDate)) return;

    router.replace({
        name: 'kiosk-inbody-list',
        params: { grade: props.grade, room: props.room, number: props.number },
        query: { startDate: startDate, endDate: endDate },
    });
};
</script>

<template lang="">
    <form class="kiosk-inbody-search-form" @submit.prevent="handleSubmit">
        <VInput
            id="kiosk-inbody-start-date"
            name="startDate"
            label="시작"
            type="date"
            size="md"
            :value="startDate"
            @input="handleInputStartDate" />
        <VInput
            id="kiosk-inbody-end-date"
            name="endDate"
            label="종료"
            type="date"
            size="md"
            :value="endDate"
            @input="handleInputEndDate" />
        <VButton text="검 색" type="submit" color="green" size="md" />
    </form>
</template>

<style lang="scss">
.kiosk-inbody-search-form {
    display: flex;
    gap: 1rem;
}
</style>
