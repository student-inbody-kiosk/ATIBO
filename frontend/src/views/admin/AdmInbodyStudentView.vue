<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import VInput from '@/components/common/VInput.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyData from '@/components/admin/inbody/InbodyData.vue';
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getTheStudentInbodys } from '@/apis/services/inbodys';
import type { InbodyDetail } from '@/types/inbody.interface';
import type { Ref } from 'vue';

const route = useRoute();

const { grade, room, number } = route.params;
const { start, end } = route.query as { start: string; end: string };
const startDate = ref('');
const endDate = ref('');
const inbodyList: Ref<InbodyDetail[]> = ref([]);
onBeforeMount(() => {
    getTheStudentInbodys(
        Number(grade),
        Number(room),
        Number(number),
        start,
        end
    ).then((res) => (inbodyList.value = res));
});
</script>

<template>
    <div class="admin-inbody-student">
        <h1>학생 인바디</h1>
        <div class="admin-inbody-container">
            <div class="admin-inbody-container__searchbar">
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
                    @click="$emit('search')" />
            </div>
            <VButton
                text="인바디 추가"
                color="green"
                @click="$router.push({ name: 'admin-student-create' })" />
        </div>
        <div class="admin-inbody-student-table-container">
            <table>
                <InbodyDataLabel />
                <InbodyData
                    v-for="(inbody, index) in inbodyList"
                    :key="inbody.id"
                    :inbody="inbody"
                    :index="index" />
            </table>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody-student {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-inbody-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.admin-inbody-container__searchbar {
    min-width: 55%;
    display: flex;
    align-items: center;
    justify-content: space-around;
}
.admin-inbody-student-table-container {
    width: 100%;
    overflow-x: auto;
}

table {
    display: flex;
}
</style>
