<script setup lang="ts">
import KioInbodyDetail from '@/components/kiosk/inbody/KioInbodyDetail.vue';
import services from '@/apis/services';
import { useRoute } from 'vue-router';
import { onBeforeMount } from 'vue';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// get data from url
const route = useRoute();
const inbodyId = Number(route.params.inbodyId);
const grade = Number(route.params.grade);
const room = Number(route.params.room);
const number = Number(route.params.number);

const inbody = await services.getInbody(inbodyId);

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
    <KioInbodyDetail class="kiosk-inbody-detail-view" :inbody="inbody" />
</template>

<style lang="scss">
.kiosk-inbody-detail-view {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    justify-items: center;
    padding: 1rem;
    background-color: $white;
    border-radius: 0.5em;
    font-size: 1.2rem;
    font-weight: 500;
}
</style>
