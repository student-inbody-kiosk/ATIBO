<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyDataInput from '@/components/admin/inbody/InbodyDataInput.vue';

import { ref, onBeforeMount } from 'vue';
import router from '@/router';
import { useRoute } from 'vue-router';
import {
    createTheStudentInbody,
    getTheStudentInbodys,
} from '@/apis/services/inbodys';

import type { Ref } from 'vue';
import type { InbodyDetail } from '@/types/inbody.interface';

const route = useRoute();

const { grade, room, number, name } = route.params;
const { start, end } = route.query as { start: string; end: string };
const startDate = ref('');
const endDate = ref('');
const inbodyList: Ref<InbodyDetail[]> = ref([]);
const updateInbodyIndex = ref(new Set());
onBeforeMount(() => {
    startDate.value = start;
    endDate.value = end;
    getTheStudentInbodys(
        Number(grade),
        Number(room),
        Number(number),
        start,
        end
    ).then((res) => (inbodyList.value = res));
});
onBeforeMount(() => {});

const handleInput = function allocateInbodyData(
    index: number,
    item: string,
    data: string | number
) {
    inbodyList.value[index][item] = data;
    updateInbodyIndex.value.add(index);
};

const handleAddClick = function addInbodyData() {
    inbodyList.value.push({
        id: null,
        testDate: '',
        weight: 0,
        percentBodyFat: 0,
        skeletalMuscleMass: 0,
        height: 0,
        age: 0,
        totalBodyWater: 0,
        protein: 0,
        minerals: 0,
        bodyFatMass: 0,
        bodyMassIndex: 0,
        score: 0,
    });
};

const handleDeleteClick = function deleteInbodyData(index: number) {
    if (!confirm('정말 삭제하시겠습니까? 되돌릴 수 없습니다.')) return;
    console.log(inbodyList.value[index]);
    if (inbodyList.value.length === 1) return;
    inbodyList.value = inbodyList.value.filter((inbody, idx) => {
        return idx !== index;
    });
};

const handleUpdateClick = function updateInbodyData() {
    const updateInbodyList = inbodyList.value.filter((inbody, idx) => {
        return updateInbodyIndex.value.has(idx);
    });

    createTheStudentInbody(updateInbodyList);
};
</script>

<template>
    <div class="admin-inbody-create">
        <div class="admin-inbody-student">
            {{ `${grade} 학년 ${room} 반 ${number} 번 ${name}` }}
        </div>
        <section class="admin-inbody-create-buttons">
            <VButton text="+ 추가" color="green" @click="handleAddClick" />
            <VButton
                text="저장"
                color="admin-primary"
                @click="handleUpdateClick" />
        </section>
        <section class="admin-inbody-create-table-container">
            <table>
                <InbodyDataLabel />
                <tbody>
                    <InbodyDataInput
                        v-for="(inbody, index) in inbodyList"
                        :key="index"
                        :index="index"
                        :inbody="inbody"
                        :isCreate="true"
                        @input="handleInput"
                        @delete="handleDeleteClick" />
                </tbody>
            </table>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody-create {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-inbody-student {
    text-align: center;
    font-size: 1.5rem;
    font-weight: 500;
}

.admin-inbody-create-buttons {
    display: flex;
    justify-content: flex-end;
    gap: 0.5rem;
}

.admin-inbody-create-table-container {
    overflow-x: auto;
}

table {
    display: flex;
}

tbody {
    display: flex;
}
</style>
