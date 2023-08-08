<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import VInput from '@/components/common/VInput.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyData from '@/components/admin/inbody/InbodyData.vue';
import InbodyCreateModal from '@/components/admin/inbody/InbodyCreateModal.vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getTheStudentInbodys } from '@/apis/services/inbodys';
import type { InbodyDetail } from '@/types/inbody.interface';
import type { Ref } from 'vue';
import router from '@/router';
import { useStudentStore } from '@/stores/student.store';

const { student, getStudent } = useStudentStore();
const route = useRoute();

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
    <div class="admin-inbody-student">
        <div class="admin-inbody-student-info">
            {{
                `${grade} 학년 ${room} 반 ${number} 번 ${name} (${
                    student?.sex === 1 ? '남' : '여'
                })`
            }}
        </div>
        <div class="admin-inbody-container">
            <div class="admin-inbody-container__header">
                <div class="admin-inbody-container__header__searchbar">
                    <VInput
                        id="startDate"
                        label="시작"
                        type="date"
                        :value="startDate"
                        @input="(value) => (startDate = value)" />
                    <VInput
                        id="endDate"
                        label="끝"
                        type="date"
                        :value="endDate"
                        @input="(value) => (endDate = value)" />
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
            @create="handleCreateClick" />
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody-student {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
}

.admin-inbody-student-info {
    font-size: 1.5rem;
    text-align: center;
}
.admin-inbody-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.admin-inbody-container__header {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.admin-inbody-container__header__searchbar {
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
