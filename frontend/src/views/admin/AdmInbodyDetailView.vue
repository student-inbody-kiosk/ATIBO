<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getInbody } from '@/apis/services/inbodys';

const route = useRoute();
const { grade, room, number, name, inbodyId } = route.params;
const inbody = ref({});
onBeforeMount(() => {
    getInbody(Number(inbodyId)).then((res) => (inbody.value = res));
});
</script>

<template>
    <div>
        <!-- TODO: Inbody Graph -->
        <h1>Inbody Detail</h1>
        <div>{{ `${grade} 학년 ${room} 반 ${number} 번 ${name}` }}</div>
        <div>{{ inbody }}</div>
    </div>
</template>

<style lang="scss" scoped></style>
