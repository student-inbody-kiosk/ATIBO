<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyDataInput from '@/components/admin/inbody/InbodyDataInput.vue';
import TheModal from '@/components/common/TheModal.vue';
import { updateInbody } from '@/apis/services/inbodys';
import type { InbodyDetail } from '@/types/inbody.interface';
import { ref } from 'vue';
import { checkInbodyInput } from '@/utils/checkInput';

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

const handleUpdateClick = function updateInbodyData() {
    const errorData = checkInbodyInput(newInbody.value);
    if (errorData !== false) return;

    updateInbody(props.inbodyId, newInbody.value).then(() => emit('update'));
};
</script>

<template>
    <TheModal @close-modal="$emit('close-modal')">
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
