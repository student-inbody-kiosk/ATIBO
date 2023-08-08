<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDetailData from '@/components/admin/inbody/InbodyDetailData.vue';
import InbodyUpdateModal from '@/components/admin/inbody/InbodyUpdateModal.vue';
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getInbody, deleteInbody } from '@/apis/services/inbodys';
import router from '@/router';
import type { InbodyDetail } from '@/types/inbody.interface';
import { useStudentStore } from '@/stores/student.store';
import { storeToRefs } from 'pinia';

const { getStudent } = useStudentStore();
const { student } = storeToRefs(useStudentStore());
const route = useRoute();
const { grade, room, number, name, inbodyId } = route.params;
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
    if (!confirm('정말 삭제하시겠습니까? 기록을 되돌릴 수 없습니다.')) return;
    deleteInbody(Number(inbodyId)).then(() =>
        router.push({
            name: 'admin-inbody-student',
            params: { grade, room, number, name },
        })
    );
};
</script>

<template>
    <div class="admin-inbody-detail">
        <!-- TODO: Inbody Graph -->
        <div class="admin-inbody-detail__buttons">
            <VButton
                text="수정"
                color="admin-primary"
                @click="handleUpdateClick" />
            <VButton text="삭제" color="red" @click="handleDeleteClick" />
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
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
}

.admin-inbody-detail__buttons {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    gap: 0.5rem;
    padding: 0.5rem 0;
}
.student-info {
    font-size: 1.5rem;
    text-align: center;
}

table {
    display: flex;
    align-items: center;
}
</style>
