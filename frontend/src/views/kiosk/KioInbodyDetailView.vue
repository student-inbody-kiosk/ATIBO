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
    <div class="kiosk-inbody-detail-view">
        <KioInbodyDetail :inbody="inbody" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-detail-view {
    height: 100%;
    padding: 1rem 2rem;
}
</style>
