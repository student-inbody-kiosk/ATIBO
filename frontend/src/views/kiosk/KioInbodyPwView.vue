<script setup lang="ts">
import { onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import KioPwChangeForm from '@/components/kiosk/inbody/KioPwChangeForm.vue';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// Get data from url
const route = useRoute();
const grade = Number(route.params.grade);
const room = Number(route.params.room);
const number = Number(route.params.number);

// Update header
onBeforeMount(() => {
    emit('update-header', {
        title: '비밀번호 변경',
        routeName: 'kiosk-inbody-list',
        routeParams: {
            grade: grade,
            room: room,
            number: number,
        },
    });
});
</script>

<template>
    <div class="kiosk-inbody-pw-view">
        <KioPwChangeForm :grade="grade" :room="room" :number="number" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-pw-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr;
    height: 100%;
    width: 100%;
    padding: 1rem 2rem;
}
</style>
