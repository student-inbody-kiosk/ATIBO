<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyDataInput from '@/components/admin/inbody/InbodyDataInput.vue';
import TheModal from '@/components/common/TheModal.vue';
import VLoading from '@/components/common/VLoading.vue';

import { ref } from 'vue';
import { useAxios } from '@/hooks/useAxios';
import services from '@/apis/services';
import { useStudentStore } from '@/stores/student.store';
import { checkInbodyInput } from '@/utils/checkInput';

import type { InbodyDetail } from '@/types/inbody.interface';

const { student } = useStudentStore();

const inbody = ref<InbodyDetail>({
    testDate: '',
    score: 0,
    age: 0,
    height: 0,
    weight: 0,
    percentBodyFat: 0,
    skeletalMuscleMass: 0,
    bodyFatMass: 0,
    bodyMassIndex: 0,
    totalBodyWater: 0,
    protein: 0,
    minerals: 0,
});

const emit = defineEmits<{
    (e: 'close-modal'): void;
    (e: 'create'): void;
}>();

const handleInput = function updateInbodyInput(
    key: string,
    value: number | string
) {
    inbody.value[key] = value;
};

const { fetchData: createInbody, isLoading } = useAxios(
    null,
    services.createInbody
);

const handleCreateClick = function updateInbodyData() {
    const errorData = checkInbodyInput(inbody.value);
    if (errorData !== false) return;

    createInbody(
        student?.grade,
        student?.room,
        student?.number,
        inbody.value
    ).then(() => emit('create'));
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <TheModal v-else @close-modal="$emit('close-modal')">
        <table>
            <caption>
                Inbody Create Table
            </caption>
            <InbodyDataLabel />
            <tbody>
                <InbodyDataInput
                    :key="inbody?.id"
                    :inbody="inbody"
                    :isCreate="true"
                    @input="handleInput" />
            </tbody>
        </table>
        <VButton text="완료" color="admin-primary" @click="handleCreateClick" />
    </TheModal>
</template>

<style lang="scss" scoped>
table {
    display: flex;
    align-items: center;
    padding: 0.5rem;
}
</style>
