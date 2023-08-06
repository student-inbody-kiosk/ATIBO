<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
import InbodyDataLabel from '@/components/admin/inbody/InbodyDataLabel.vue';
import InbodyDataInput from '@/components/admin/inbody/InbodyDataInput.vue';
import { getInbody } from '@/apis/services/inbodys';
import { ref, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import type { InbodyDetail } from '@/types/inbody.interface';
const route = useRoute();

const { grade, room, number, name, inbodyId } = route.params;

const inbody = ref<InbodyDetail>();
onBeforeMount(() => {
    onBeforeMount(() => {
        getInbody(Number(inbodyId)).then((res) => (inbody.value = res));
    });
});

const handleUpdateClick = function updateInbodyData() {
    console.log('update');
};
</script>

<template>
    <div class="admin-inbody-create">
        <h1 class="admin-inbody-student">
            {{ `${grade} 학년 ${room} 반 ${number} 번 ${name}` }}
        </h1>
        <section class="admin-inbody-create-buttons">
            <VButton
                text="저장"
                color="admin-primary"
                @click="handleUpdateClick" />
        </section>
        <section class="admin-inbody-create-table-container">
            <!-- <table>
                <InbodyDataLabel />
                <tbody>
                    <InbodyDataInput
                        :key="inbody?.id"
                        :inbody="inbody"
                        :isCreate="true"
                        @input="handleInput" />
                </tbody>
            </table> -->
            {{ inbody }}
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
</style>
