<script setup lang="ts">
import { ref, onBeforeMount, watch } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import services from '@/apis/services';
import regexes from '@/constants/regexes';
import VIconButton from '@/components/common/VIconButton.vue';
import KioInbodyTable from '@/components/kiosk/inbody/KioInbodyTable.vue';
import KioInbodyGraph from '@/components/kiosk/inbody/KioInbodyGraph.vue';
import KioInbodySearchForm from '@/components/kiosk/inbody/KioInbodySearchForm.vue';
import KioInbodyButtons from '@/components/kiosk/inbody/KioInbodyButtons.vue';
import { useStudentStore } from '@/stores/student.store';
import { getToday, getAYearAgo } from '@/utils/date';
import type { HeaderUpdate } from '@/types/app.interface';
import type { InbodyDetail } from '@/types/inbody.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// get data from url
const route = useRoute();
// // from params
const grade = Number(route.params.grade);
const room = Number(route.params.room);
const number = Number(route.params.number);
// // from query
const startDate = ref(
    regexes.date.test(String(route.query.startDate))
        ? String(route.query.startDate)
        : getAYearAgo()
);
const endDate = ref(
    regexes.date.test(String(route.query.endDate))
        ? String(route.query.endDate)
        : getToday()
);

// student from pinia store
const studentStore = useStudentStore();
const { student } = storeToRefs(useStudentStore());

const isGraph = ref(false);

const handleUpdateIsGraph = function updateIsTable(value: boolean) {
    isGraph.value = value;
};

// get the intial inbody data
const inbodys = ref<InbodyDetail[]>(
    await services.getTheStudentInbodys(
        grade,
        room,
        number,
        startDate.value,
        endDate.value
    )
);

// update startDate and endDate
watch(
    () => route.query,
    async (newQuery) => {
        const newStartDate = String(newQuery.startDate);
        const newEndDate = String(newQuery.endDate);
        if (regexes.date.test(newStartDate)) {
            startDate.value = newStartDate;
        }
        if (regexes.date.test(newEndDate)) {
            endDate.value = newEndDate;
        }
    }
);

// update inbody data
watch([startDate, endDate], async () => {
    inbodys.value = await services.getTheStudentInbodys(
        grade,
        room,
        number,
        startDate.value,
        endDate.value
    );
    emit('update-header', {
        routeQuery: {
            startDate: startDate.value,
            endDate: endDate.value,
        },
    });
});

// update kio-header
watch(student, () => {
    if (!studentStore.student?.name) return;
    emit('update-header', {
        title: `${studentStore.student?.name} 님의 인바디`,
    });
});

// update kio-header
onBeforeMount(() => {
    emit('update-header', {
        routeName: 'kiosk-inbody',
    });
    if (!studentStore.student?.name) return;
    emit('update-header', {
        title: `${studentStore.student?.name} 님의 인바디`,
    });
});
</script>

<template>
    <div class="kiosk-inbody-list-view">
        <div class="kiosk-inbody-list-view__inputs">
            <KioInbodyButtons
                :is-graph="isGraph"
                @update-is-graph="handleUpdateIsGraph" />
            <KioInbodySearchForm
                :grade="grade"
                :room="room"
                :number="number"
                :startDate="startDate"
                :endDate="endDate" />
        </div>
        <KioInbodyTable v-if="!isGraph" :inbodys="inbodys" />
        <KioInbodyGraph v-else :inbodys="inbodys" />
        <div class="kiosk-inbody-list-view__password">
            <RouterLink :to="{ name: 'kiosk-inbody-pw' }">
                <VIconButton>
                    <font-awesome-icon icon="lock" size="3x" />
                </VIconButton>
            </RouterLink>
        </div>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    height: 100%;
    padding: 1rem;
}

.kiosk-inbody-list-view__inputs {
    display: flex;
    justify-content: space-between;
    padding: 0 0 1rem 0;
}

.kiosk-inbody-list-view__password {
    position: absolute;
    top: 0.8rem;
    right: 2rem;
}
</style>
