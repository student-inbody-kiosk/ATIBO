<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import services from '@/apis/services';
import type { Header } from '@/types/app.interface';
import type { InbodyDetail } from '@/types/inbody.interface';
import { useRoute } from 'vue-router';
import KioInbodyList from '@/components/kiosk/inbody/KioInbodyList.vue';

const emit = defineEmits<{
    (e: 'update-header', info: Header): void;
}>();

const route = useRoute();
const grade = Number(route.params.grade);
const room = Number(route.params.grade);
const number = Number(route.params.grade);

const inbodys = ref<InbodyDetail[]>(
    await services.getTheStudentInbodys(grade, room, number)
);

onBeforeMount(() => {
    emit('update-header', { title: '인바디 목록', routeName: 'kiosk-inbody' });
});
</script>

<template>
    <div class="kiosk-inbody-list-view">
        <KioInbodyList :inbodys="inbodys" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list-view {
    height: 100%;
    padding: 1rem;
}
</style>
