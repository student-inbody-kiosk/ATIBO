<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import { onBeforeMount, ref, onMounted, watch, inject } from 'vue';
import services from '@/apis/services';
import { useRoute, useRouter } from 'vue-router';
import type { InbodyDetail } from '@/types/inbody.interface';

const props = defineProps<{
    grade: number;
    room: number;
    number: number;
    startDate: string;
    endDate: string;
}>();

const router = useRouter();

// set the initail input data
const startDate = ref(props.startDate);
const endDate = ref(props.endDate);

const handleInputStartDate = function (data: string) {
    startDate.value = data;
};

const handleInputEndDate = function (data: string) {
    endDate.value = data;
};

const handleSubmit = function getTheStudentInbodys() {
    router.replace({
        name: 'kiosk-inbody-list',
        params: { grade: props.grade, room: props.room, number: props.number },
        query: { startDate: startDate.value, endDate: endDate.value },
    });
};
</script>

<template lang="">
    <form class="kiosk-inbody-search-form" @submit.prevent="handleSubmit">
        <VInput
            id="kiosk-inbody-start-date"
            label="시작일"
            type="date"
            size="md"
            :value="startDate"
            @input="handleInputStartDate" />
        <VInput
            id="kiosk-inbody-end-date"
            label="종료일"
            type="date"
            size="md"
            :value="endDate"
            @input="handleInputEndDate" />
        <VButton text="검색" type="submit" color="green" size="md" />
    </form>
</template>

<style lang="scss">
.kiosk-inbody-search-form {
    display: flex;
    gap: 1rem;
}
</style>
