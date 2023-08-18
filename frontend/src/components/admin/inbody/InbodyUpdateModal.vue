<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyDataInput from '@/components/admin/inbody/InbodyDataInput.vue';
import TheModal from '@/components/common/TheModal.vue';
import VLoading from '@/components/common/VLoading.vue';

import { ref } from 'vue';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { checkInbodyInput } from '@/utils/checkInput';

import type { InbodyDetail } from '@/types/inbody.interface';

const props = defineProps<{
    inbodyId: number;
    inbody: InbodyDetail;
}>();

const emit = defineEmits<{
    (e: 'close-modal'): void;
    (e: 'update'): void;
}>();

const newInbody = ref<InbodyDetail>(props.inbody);

const handleInput = function updateInbodyInput(
    key: string,
    value: number | string
) {
    newInbody.value[key] = value;
};

const { fetchData: updateInbody, isLoading } = useAxios(
    null,
    services.updateInbody
);

const handleUpdateClick = function updateInbodyData() {
    const errorData = checkInbodyInput(newInbody.value);
    // 정규식 검사 끝난 후 number로 형변환
    if (errorData !== false) return;

    updateInbody(props.inbodyId, newInbody.value).then(() => emit('update'));
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <TheModal v-else @close-modal="$emit('close-modal')">
        <table>
            <InbodyDataLabel />
            <tbody>
                <InbodyDataInput
                    :key="inbody?.id"
                    :inbody="inbody"
                    :isCreate="true"
                    @input="handleInput" />
            </tbody>
        </table>
        <VButton text="완료" color="admin-primary" @click="handleUpdateClick" />
    </TheModal>
</template>

<style lang="scss" scoped>
table {
    display: flex;
    align-items: center;
    padding: 0.5rem 0;
}
</style>
