<script setup lang="ts">
import { onBeforeMount, ref, onMounted, watch, inject } from 'vue';
import services from '@/apis/services';
import type { Header } from '@/types/app.interface';
import type { InbodyDetail } from '@/types/inbody.interface';
import { useRoute } from 'vue-router';
import KioInbodyList from '@/components/kiosk/inbody/KioInbodyList.vue';
import { useStudentStore } from '@/stores/student.store.ts';
import VIconButton from '@/components/common/VIconButton.vue';
import { storeToRefs } from 'pinia';
import { getToday, getAYearAgo } from '@/utils/date';

const emit = defineEmits<{
    (e: 'update-header', info: Header): void;
}>();

const route = useRoute();
const grade = Number(route.params.grade);
const room = Number(route.params.grade);
const number = Number(route.params.grade);

const startDate = ref(getAYearAgo());
const endDate = ref(getToday());

const studentStore = useStudentStore();
const { student } = storeToRefs(studentStore);

const inbodys = ref<InbodyDetail[]>(
    await services.getTheStudentInbodys(
        grade,
        room,
        number,
        startDate.value,
        endDate.value
    )
);

studentStore.getStudent(grade, room, number);

watch(student, () => {
    emit('update-header', {
        title: `${studentStore.student?.name} 님의 인바디`,
        routeName: 'kiosk-inbody',
    });
});
</script>

<template>
    <div class="kiosk-inbody-list-view">
        <p>표, 그래프, input 데이터</p>

        <KioInbodyList :inbodys="inbodys" />
        <div class="kiosk-inbody-list-view__password">
            <VIconButton>
                <font-awesome-icon icon="lock" size="3x" />
            </VIconButton>
        </div>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list-view {
    height: 100%;
    padding: 1rem;
}

.kiosk-inbody-list-view__password {
    position: absolute;
    top: 0.8rem;
    right: 2rem;
}
</style>
