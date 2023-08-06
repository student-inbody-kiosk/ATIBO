<script setup lang="ts">
import { onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import services from '@/apis/services';
import KioInbodyDetail from '@/components/kiosk/inbody/KioInbodyDetail.vue';
import { useStudentStore } from '@/stores/student.store';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// Get data from url
const route = useRoute();
const inbodyId = Number(route.params.inbodyId);
const grade = Number(route.params.grade);
const room = Number(route.params.room);
const number = Number(route.params.number);

// Get Inbody data asynchronously
const inbody = await services.getInbody(inbodyId);

// Get the Student data(name, sex) from pinia store
const { student } = useStudentStore();

onBeforeMount(() => {
    emit('update-header', {
        title: '인바디 상세',
        routeName: 'kiosk-inbody-list',
        routeParams: {
            grade: grade,
            room: room,
            number: number,
        },
    });
});
</script>

<template lang="">
    <div class="kiosk-inbody-detail-view">
        <KioInbodyDetail
            v-if="student"
            :name="student.name"
            :sex="student.sex"
            :inbody="inbody" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-detail-view {
    height: 100%;
    padding: 1rem 2rem;
}
</style>
