<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { storeToRefs } from 'pinia';
import { useRoute } from 'vue-router';
import regexes from '@/constants/regexes';
import VIconButton from '@/components/common/VIconButton.vue';
import KioInbodyList from '@/components/kiosk/inbody/KioInbodyList.vue';
import KioInbodySearchForm from '@/components/kiosk/inbody/KioInbodySearchForm.vue';
import { useStudentStore } from '@/stores/student.store';
import { getToday, getAYearAgo } from '@/utils/date';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

const route = useRoute();

// Get grade, room, number from URL
const grade = Number(route.params.grade);
const room = Number(route.params.room);
const number = Number(route.params.number);

/* Managing startDate & endDate logic */

// Default startDate, endDate
const startDate = ref(
    regexes.date.test(String(route.query.startDate))
        ? String(route.query.startDate)
        : getAYearAgo() // The default startDate: a year ago
);
const endDate = ref(
    regexes.date.test(String(route.query.endDate))
        ? String(route.query.endDate)
        : getToday() // The default endDate: today
);

// Update startDate and endDate according to query
// Update kioHeader query params
watch(
    () => route.query,
    (newQuery) => {
        const newStartDate = String(newQuery.startDate);
        const newEndDate = String(newQuery.endDate);
        if (regexes.date.test(newStartDate)) {
            startDate.value = newStartDate;
        }
        if (regexes.date.test(newEndDate)) {
            endDate.value = newEndDate;
        }
        emit('update-header', {
            routeQuery: {
                startDate: startDate.value,
                endDate: endDate.value,
            },
        });
    }
);

/* Update kio-header according to student name */
const studentStore = useStudentStore();
const { student } = storeToRefs(useStudentStore()); // from pinia store

// When mounted
onMounted(() => {
    emit('update-header', {
        routeName: 'kiosk-inbody',
    });
    if (!studentStore.student?.name) return;
    emit('update-header', {
        title: `${studentStore.student?.name} 님의 인바디`,
    });
});

// When the student data is changed
watch(student, () => {
    if (!studentStore.student?.name) return;
    emit('update-header', {
        title: `${studentStore.student?.name} 님의 인바디`,
    });
});
</script>

<template>
    <div class="kiosk-inbody-list-view">
        <KioInbodySearchForm
            class="kiosk-inbody-list-view__input"
            :grade="grade"
            :room="room"
            :number="number"
            :startDate="startDate"
            :endDate="endDate" />
        <KioInbodyList
            :grade="grade"
            :room="room"
            :number="number"
            :startDate="startDate"
            :endDate="endDate" />
        <RouterLink
            class="kiosk-inbody-list-view__password"
            :to="{ name: 'kiosk-inbody-pw' }">
            <VIconButton>
                <font-awesome-icon icon="lock" size="3x" />
            </VIconButton>
        </RouterLink>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list-view {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: minmax(0, 1fr);
    position: relative;
    height: 100%;
    padding: 1rem;
}

.kiosk-inbody-list-view__input {
    position: absolute;
    top: 0.8rem;
    right: 2rem;
}

.kiosk-inbody-list-view__password {
    position: fixed;
    top: 1.5rem;
    right: 2rem;
}
</style>
