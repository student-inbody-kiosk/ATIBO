<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getTheStudentInbodys } from '@/apis/services/inbodys';

const route = useRoute();
const { grade, room, number } = route.params;
const { start, end } = route.query as { start: string; end: string };
const inbodyList = ref({});
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
    <div>
        <h1>학생 인바디</h1>
        <div>{{ inbodyList }}</div>
    </div>
</template>

<style lang="scss" scoped></style>
