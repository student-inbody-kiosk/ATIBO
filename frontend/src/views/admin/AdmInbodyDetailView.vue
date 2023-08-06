<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getInbody } from '@/apis/services/inbodys';
import router from '@/router';

const route = useRoute();
const { grade, room, number, name, inbodyId } = route.params;
const inbody = ref({});

onBeforeMount(() => {
    getInbody(Number(inbodyId)).then((res) => (inbody.value = res));
});

const handleUpdateClick = function routeToInbodyUpdate() {
    router.push({ name: 'admin-inbody-update', params: route.params });
};

const handleDeleteClick = function deleteInbody() {
    if (!confirm('정말 삭제하시겠습니까? 기록을 되돌릴 수 없습니다.')) return;
};
</script>

<template>
    <div>
        <!-- TODO: Inbody Graph -->
        <h1 class="student-info">
            {{ `${grade} 학년 ${room} 반 ${number} 번 ${name}` }}
        </h1>
        <VButton text="수정" color="admin-primary" @click="handleUpdateClick" />
        <VButton text="삭제" color="red" @click="handleDeleteClick" />
        <div>{{ inbody }}</div>
    </div>
</template>

<style lang="scss" scoped>
.student-info {
    font-size: 1.5rem;
    text-align: center;
}
</style>
