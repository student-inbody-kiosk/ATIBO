<script setup lang="ts">
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Title,
} from 'chart.js';
import { Line } from 'vue-chartjs';
import { inbodyToLineData, lineOptions } from '@/utils/chartjs';
import type { InbodyDetail } from '@/types/inbody.interface';
import { computed } from 'vue';

const props = defineProps<{
    inbodys: InbodyDetail[];
}>();

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Title
);

ChartJS.defaults.font.size = 15;

const graphData = computed(() => {
    return inbodyToLineData(props.inbodys);
});
</script>

<template>
    <div class="kiosk-inbody-graph">
        <div class="kiosk-inbody-graph__container">
            <div class="kiosk-inbody-graph__list">
                <h1 class="kiosk-inbody-graph__title">체중(kg)</h1>
                <Line :data="graphData.weightData" :options="lineOptions" />
                <h1 class="kiosk-inbody-graph__title">체지방률(%)</h1>
                <Line
                    :data="graphData.percentBodyFatData"
                    :options="lineOptions" />
                <h1 class="kiosk-inbody-graph__title">골격근량(kg)</h1>
                <Line
                    :data="graphData.skeletalMuscleMassData"
                    :options="lineOptions" />
                <h1 class="kiosk-inbody-graph__title">인바디 점수</h1>
                <Line :data="graphData.scoreData" :options="lineOptions" />
            </div>
        </div>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-graph {
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: minmax(0, 1fr);
    height: 100%;
    width: 100%;
    border: $kiosk-primary 5px solid;
    border-radius: 1rem;
}

.kiosk-inbody-graph canvas {
    background-color: transparentize($white, 0.2);
    border-radius: 1em;
    max-height: 30rem;
}

.kiosk-inbody-graph__container {
    padding: 0 0.4rem 0 0;
}

.kiosk-inbody-graph__list {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    overflow: auto;
    height: 100%;
    width: 100%;
    padding: 1rem 0.6rem 1rem 1rem;
}

.kiosk-inbody-graph__title {
    align-self: center;
    margin-top: 1rem;
    font-size: 1.5rem;
    font-weight: 600;
}
</style>
@/utils/chartjs
