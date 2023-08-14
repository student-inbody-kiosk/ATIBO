<script setup lang="ts">
import { ref, watchEffect } from 'vue';
import services from '@/apis/services';
import VButton from '@/components/common/VButton.vue';
import VLoading from '@/components/common/VLoading.vue';
import KioInbodyTable from '@/components/kiosk/inbody/KioInbodyTable.vue';
import KioInbodyGraph from '@/components/kiosk/inbody/KioInbodyGraph.vue';
import { useAxios } from '@/hooks/useAxios';
import type { InbodyDetail } from '@/types/inbody.interface';

const props = defineProps<{
    grade: number;
    room: number;
    number: number;
    startDate: string;
    endDate: string;
}>();

/* Inbody data logic */
const {
    fetchData: getTheStudentInbodys,
    response: inbodys,
    isLoading,
    isError,
} = useAxios<InbodyDetail[]>([], services.getTheStudentInbodys);

// Get the intial inbody data Asynchronously
await getTheStudentInbodys(
    props.grade,
    props.room,
    props.number,
    props.startDate,
    props.endDate
);

// Update inbody data when props changed Asynchronously
watchEffect(async () => {
    await getTheStudentInbodys(
        props.grade,
        props.room,
        props.number,
        props.startDate,
        props.endDate
    );
});

/* Change the mode */
const isGraph = ref(false);

// table mode
const handleClickTable = function changeIsGraphFalse() {
    isGraph.value = false;
};

// graph mode
const handleClickGraph = function changeIsGraphTrue() {
    isGraph.value = true;
};
</script>

<template>
    <VLoading v-if="isLoading" color="kiosk-primary" class="kiosk-loading" />
    <div class="kiosk-inbody-list">
        <div class="kiosk-inbody-list__buttons">
            <VButton
                text="표"
                :color="isGraph ? 'gray' : 'kiosk-primary'"
                size="md"
                @click="handleClickTable" />
            <VButton
                text="그래프"
                :color="!isGraph ? 'gray' : 'kiosk-primary'"
                size="md"
                @click="handleClickGraph" />
        </div>
        <KioInbodyTable v-if="!isGraph" :inbodys="inbodys" />
        <KioInbodyGraph v-else :inbodys="inbodys" />
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    row-gap: 1rem;
    height: 100%;
    padding: 0 1rem 1rem 1rem;
}

.kiosk-inbody-list__buttons {
    display: flex;
    gap: 1rem;
}
</style>
