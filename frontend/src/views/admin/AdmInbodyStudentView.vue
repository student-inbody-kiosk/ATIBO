<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import VInput from '@/components/common/VInput.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyData from '@/components/admin/inbody/InbodyData.vue';
import InbodyCreateModal from '@/components/admin/inbody/InbodyCreateModal.vue';
import VLoading from '@/components/common/VLoading.vue';

import router from '@/router';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStudentStore } from '@/stores/student.store';
import { checkSearchInput } from '@/utils/checkInput';

import type { Ref } from 'vue';
import type { InbodyDetail } from '@/types/inbody.interface';

const route = useRoute();
const { fetchData: getTheStudentInbodys, isLoading } = useAxios(
    null,
    services.getTheStudentInbodys
);
const { student, getStudent } = useStudentStore();

const { grade, room, number, name } = route.params;
const { start, end } = route.query as { start: string; end: string };
const startDate = ref('');
const endDate = ref('');
const isCreateModalOpen = ref(false);
const inbodyList: Ref<InbodyDetail[]> = ref([]);

onMounted(() => {
    startDate.value = start;
    endDate.value = end;
    getTheStudentInbodys(
        Number(grade),
        Number(room),
        Number(number),
        start,
        end
    ).then((res) => (inbodyList.value = res));

    // 학생 정보 pinia에 저장
    getStudent(Number(grade), Number(room), Number(number)); // pinia 학생 정보 저장
});

const handleSearchClick = function searchInbodyList() {
    const data = { startDate: startDate.value, endDate: endDate.value };
    if (checkSearchInput(data)) return;

    getTheStudentInbodys(
        Number(grade),
        Number(room),
        Number(number),
        startDate.value,
        endDate.value
    ).then((res) => (inbodyList.value = res));
};

const handleDataClick = function routeToInbodyDetail(inbodyId: number) {
    router.push({
        name: 'admin-inbody-detail',
        params: { grade, room, number, name, inbodyId },
    });
};

const handleCreateClick = function updateTheStudentInbodys() {
    getTheStudentInbodys(
        Number(grade),
        Number(room),
        Number(number),
        start,
        end
    ).then((res) => (inbodyList.value = res));
    isCreateModalOpen.value = false;
};
</script>

<template>
    <VLoading v-if="isLoading" color="admin-primary" />
    <div v-else class="admin-inbody-student">
        <div class="admin-inbody-student-info">
            <VButton
                text="뒤로"
                color="gray"
                @click="router.push({ name: 'admin-inbody' })" />
            <span>{{
                `${grade} 학년 ${room} 반 ${number} 번 ${name} (${
                    student?.sex === 1 ? '남' : '여'
                })`
            }}</span>
        </div>

        <div class="admin-inbody-header">
            <div class="admin-inbody-header__searchbar">
                <VInput
                    id="startDate"
                    label="시작"
                    type="date"
                    :value="startDate"
                    size="md"
                    @input="(value) => (startDate = value)"
                    @enter="handleSearchClick" />
                <VInput
                    id="endDate"
                    label="끝"
                    type="date"
                    :value="endDate"
                    size="md"
                    @input="(value) => (endDate = value)"
                    @enter="handleSearchClick" />
                <VButton
                    text="조회"
                    color="admin-primary"
                    @click="handleSearchClick" />
            </div>
            <VButton
                text="기록 추가"
                color="green"
                @click="() => (isCreateModalOpen = true)" />
        </div>

        <div class="admin-inbody-student-table-container">
            <table>
                <InbodyDataLabel />
                <tbody>
                    <InbodyData
                        v-for="(inbody, index) in inbodyList"
                        :key="inbody.id"
                        :inbody="inbody"
                        :index="index"
                        @click="handleDataClick" />
                </tbody>
            </table>
        </div>
        <InbodyCreateModal
            v-if="isCreateModalOpen"
            @create="handleCreateClick"
            @close-modal="isCreateModalOpen = false" />
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody-student {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
    gap: 2rem;
}

.admin-inbody-student-info {
    display: grid;
    grid-template-columns: auto minmax(0, 1fr);
    grid-template-rows: 1fr;
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 1rem;
}

.admin-inbody-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-bottom: 1rem;
}
.admin-inbody-header__searchbar {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.admin-inbody-student-table-container {
    width: 100%;
    overflow-x: auto;
}

table,
tbody {
    display: flex;
}
</style>
