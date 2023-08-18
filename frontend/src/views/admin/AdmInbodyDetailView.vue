<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDetailData from '@/components/admin/inbody/InbodyDetailData.vue';
import InbodyUpdateModal from '@/components/admin/inbody/InbodyUpdateModal.vue';
import VLoading from '@/components/common/VLoading.vue';

import { onBeforeMount, ref } from 'vue';
import services from '@/apis/services';
import { useAxios } from '@/hooks/useAxios';
import { useRoute } from 'vue-router';
import { useStudentStore } from '@/stores/student.store';
import { storeToRefs } from 'pinia';
import router from '@/router';

import type { InbodyDetail } from '@/types/inbody.interface';

const route = useRoute();
const { getStudent } = useStudentStore();
const { student } = storeToRefs(useStudentStore());
const { fetchData: getInbody, isLoading: isGetInbodyLoading } = useAxios(
    null,
    services.getInbody
);
const { fetchData: deleteInbody, isLoading: isDeleteInbodyLoading } = useAxios(
    null,
    services.deleteInbody
);
const { grade, room, number, name, sex, inbodyId } = route.params;
const inbody = ref<InbodyDetail>();
const isUpdateModalOpen = ref(false);

// 인바디 데이터 조회 API
const getInbodyAPI = () => {
    getInbody(Number(inbodyId)).then((res) => (inbody.value = res));
};

onBeforeMount(() => {
    getInbodyAPI();
    getStudent(Number(grade), Number(room), Number(number)); // pinia 학생 정보 저장
});

// 수정 전 인바디 최신 데이터 한 번 더 불러오기
const handleUpdateClick = function getRecentInbodyandOpenModal() {
    getInbodyAPI();
    isUpdateModalOpen.value = true;
};

// 수정 후 최신 인바디 기록 불러오기
const handleAfterUpdate = function getUpdatedInbody() {
    getInbodyAPI();
    isUpdateModalOpen.value = false;
};

// 인바디 삭제
const handleDeleteClick = function deleteInbodyData() {
    if (!confirm('정말 삭제하시겠습니까? 기록을 되돌릴 수 없습니다')) return;
    deleteInbody(Number(inbodyId)).then(() =>
        router.push({
            name: 'admin-inbody-student',
            params: { grade, room, number, name },
        })
    );
};
</script>

<template>
    <VLoading
        v-if="isGetInbodyLoading || isDeleteInbodyLoading"
        color="admin-primary" />
    <div v-else class="admin-inbody-detail">
        <div class="admin-inbody-detail__buttons">
            <VButton text="뒤로" color="gray" @click="router.go(-1)" />
            <div class="admin-inbody-detail__personal">
                <p>이름: {{ name }}</p>
                <p>나이: {{ inbody?.age }}</p>
                <p>키: {{ inbody?.height }}cm</p>
                <p>성별: {{ student?.sex === 1 ? '남' : '여' }}</p>
                <p>날짜: {{ inbody?.testDate }}</p>
            </div>
            <div>
                <VButton
                    text="수정"
                    color="admin-primary"
                    @click="handleUpdateClick" />
                <VButton text="삭제" color="red" @click="handleDeleteClick" />
            </div>
        </div>
        <InbodyDetailData
            v-if="student && inbody"
            :name="student.name"
            :inbody="inbody"
            :sex="student.sex" />
        <InbodyUpdateModal
            v-if="isUpdateModalOpen && inbody"
            :inbodyId="Number(inbodyId)"
            :inbody="inbody"
            @update="handleAfterUpdate"
            @close-modal="isUpdateModalOpen = false" />
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody-detail {
    width: 100%;
    min-width: 800px;
    height: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-inbody-detail__personal {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    font-weight: 600;
    font-size: 1.4rem;
}

.admin-inbody-detail__buttons {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0.5rem 0;

    div {
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }
}
.student-info {
    font-size: 1.5rem;
    text-align: center;
}
</style>
