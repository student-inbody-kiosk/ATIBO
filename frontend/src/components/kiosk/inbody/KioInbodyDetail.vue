<script setup lang="ts">
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    BarElement,
    CategoryScale,
    LinearScale,
} from 'chart.js';
import { Bar } from 'vue-chartjs';
import type { InbodyDetail } from '@/types/inbody.interface';
import { inbodyToBarData, barOptions } from '@/utils/chartjs';
import { getAverageValue, getMaxValue, getMinValue } from '@/utils/inbody';
import { computed } from 'vue';
import { useStudentStore } from '@/stores/student.store';

const props = defineProps<{
    inbody: InbodyDetail;
}>();

// Get the Student data(name, sex) from pinia store
const { student } = useStudentStore();

/* InbodyDetail -> chartJS barGraph dataset */
const barData = computed(() => {
    return inbodyToBarData(props.inbody);
});

/* chartJS setting */
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip);

// Calculate personal avg, min, max data
const avgValue = getAverageValue(props.inbody, student?.sex ? student.sex : 1);
const minValue = getMinValue(props.inbody, student?.sex ? student.sex : 1);
const maxValue = getMaxValue(props.inbody, student?.sex ? student.sex : 1);

// Set the cacluated data to the graph
const weightOptions = JSON.parse(JSON.stringify(barOptions));
weightOptions.scales.x.suggestedMin = minValue.weight;
weightOptions.scales.x.suggestedMax = maxValue.weight;

const skeletalMuscleMassOptions = JSON.parse(JSON.stringify(barOptions));
skeletalMuscleMassOptions.scales.x.suggestedMin = minValue.skeletalMuscleMass;
skeletalMuscleMassOptions.scales.x.suggestedMax = maxValue.skeletalMuscleMass;

const bodyFatMassOptions = JSON.parse(JSON.stringify(barOptions));
bodyFatMassOptions.scales.x.suggestedMin = minValue.bodyFatMass;
bodyFatMassOptions.scales.x.suggestedMax = maxValue.bodyFatMass;

const bodyMassIndexOptions = JSON.parse(JSON.stringify(barOptions));
bodyMassIndexOptions.scales.x.suggestedMin = minValue.bodyMassIndex;
bodyMassIndexOptions.scales.x.suggestedMax = maxValue.bodyMassIndex;

const percentBodyFatOptions = JSON.parse(JSON.stringify(barOptions));
percentBodyFatOptions.scales.x.suggestedMin = minValue.percentBodyFat;
percentBodyFatOptions.scales.x.suggestedMax = maxValue.percentBodyFat;
</script>

<template lang="">
    <div class="kiosk-inbody-detail">
        <div class="kiosk-inbody-detail__personal">
            <p>이름: {{ student.name }}</p>
            <p>나이: {{ inbody.age }}</p>
            <p>키: {{ inbody.height }}cm</p>
            <p>날짜: {{ inbody.testDate }}</p>
        </div>
        <div class="kiosk-inbody-detail__comment">
            <p>* 본 결과지는 간이 결과지입니다.</p>
            <p>
                * '키' 값을 임력하지 않은 경우, 청소년 평균 키 값으로 계산됩니다
            </p>
        </div>
        <div class="kiosk-inbody-detail--container">
            <div class="kiosk-inbody-detail__composition">
                <h2 class="kiosk-inbody-detail__subtitle">체성분 분석</h2>
                <div class="kiosk-inbody-detail__composition--container">
                    <p class="kiosk-inbody-detail__compositoin__label">
                        <span>우리 몸을 이루는 물</span><span> 체수분</span>
                    </p>
                    <p>{{ inbody.totalBodyWater }} L</p>
                    <p class="kiosk-inbody-detail__compositoin__label">
                        <span>근육을 만들어 주는</span> <span>단백질</span>
                    </p>
                    <p>{{ inbody.protein }} kg</p>
                    <p class="kiosk-inbody-detail__compositoin__label">
                        <span>뼈를 단단하게 하는</span> <span>무기질</span>
                    </p>
                    <p>{{ inbody.minerals }} kg</p>
                    <p class="kiosk-inbody-detail__compositoin__label">
                        <span>남은 에너지를 저장한</span>
                        <span>체지방</span>
                    </p>
                    <p>{{ inbody.bodyFatMass }} kg</p>
                    <p class="kiosk-inbody-detail__compositoin__label">
                        <span>위의 모든 값을 합한</span> <span>체중</span>
                    </p>
                    <p>{{ inbody.weight }} kg</p>
                </div>
            </div>
            <div class="kiosk-inbody-detail__muscle-fat">
                <h2 class="kiosk-inbody-detail__subtitle">골격근 지방 분석</h2>
                <div class="kiosk-inbody-detail__muscle-fat--container">
                    <p class="kiosk-inbody-detail__bar-label">
                        체중<br />
                        {{ inbody.weight }} kg
                    </p>
                    <div>
                        <Bar
                            :data="barData.weightData"
                            :options="weightOptions" />
                    </div>
                    <p class="kiosk-inbody-detail__bar-label">
                        골격근량 <br />
                        {{ inbody.skeletalMuscleMass }} kg
                    </p>
                    <div>
                        <Bar
                            :data="barData.skeletalMuscleMassData"
                            :options="skeletalMuscleMassOptions" />
                    </div>
                    <p class="kiosk-inbody-detail__bar-label">
                        체지방량<br />
                        {{ inbody.bodyFatMass }} kg
                    </p>
                    <div>
                        <Bar
                            :data="barData.bodyFatMassData"
                            :options="bodyFatMassOptions" />
                    </div>
                </div>
            </div>
            <div class="kiosk-inbody-detail__obesity">
                <h2 class="kiosk-inbody-detail__subtitle">비만 분석</h2>
                <div class="kiosk-inbody-detail__obesity--container">
                    <p class="kiosk-inbody-detail__bar-label">
                        BMI<br />
                        {{ inbody.bodyMassIndex }}
                    </p>
                    <div>
                        <Bar
                            :data="barData.bodyMassIndexData"
                            :options="bodyMassIndexOptions" />
                    </div>
                    <p class="kiosk-inbody-detail__bar-label">
                        체지방률<br />
                        {{ inbody.percentBodyFat }} %
                    </p>
                    <div>
                        <Bar
                            :data="barData.percentBodyFatData"
                            :options="percentBodyFatOptions" />
                    </div>
                </div>
            </div>
            <div class="kiosk-inbody-detail__total">
                <p class="kiosk-inbody-detail__total__label">
                    <span>인바디 점수</span
                    ><span class="kiosk-inbody-detail__total__score">
                        {{ inbody.score }}</span
                    >
                </p>
                <p class="kiosk-inbody-detail__total__label">
                    <span>적정 체중</span
                    ><span> {{ avgValue.weight.toFixed(2) }} kg</span>
                </p>
                <p class="kiosk-inbody-detail__total__label">
                    <span>체중 조절</span
                    ><span>
                        {{ (avgValue.weight - inbody.weight).toFixed(2) }}
                        kg</span
                    >
                </p>
                <p class="kiosk-inbody-detail__total__label">
                    <span>지방 조절</span
                    ><span>
                        {{
                            (avgValue.bodyFatMass - inbody.bodyFatMass).toFixed(
                                2
                            )
                        }}
                        kg</span
                    >
                </p>
                <p class="kiosk-inbody-detail__total__label">
                    <span>근육 조절</span
                    ><span>
                        {{
                            (
                                avgValue.skeletalMuscleMass -
                                inbody.skeletalMuscleMass
                            ).toFixed(2)
                        }}
                        kg</span
                    >
                </p>
                <p class="kiosk-inbody-detail__total__label">
                    <span> BMI</span
                    ><span>
                        {{
                            inbody.bodyMassIndex > avgValue.maxBodyMassIndex
                                ? '과체중'
                                : inbody.bodyMassIndex <
                                  avgValue.minBodyMassIndex
                                ? '저체중'
                                : '표준'
                        }}</span
                    >
                </p>
                <p class="kiosk-inbody-detail__total__label">
                    <span> 체지방률 </span>
                    <span>
                        {{
                            inbody.percentBodyFat > avgValue.maxPercentBodyFat
                                ? '표준 이상'
                                : inbody.percentBodyFat <
                                  avgValue.minPercentBodyFat
                                ? '표준 이하'
                                : '표준'
                        }}</span
                    >
                </p>
            </div>
        </div>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-detail {
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: auto auto minmax(0, 1fr);
    width: 100%;
    height: 100%;
    padding: 1rem;
    background-color: white;
    border-radius: 1em;
    overflow: auto;
    font-size: 1.2rem;
    font-weight: 500;
}

.kiosk-inbody-detail::-webkit-scrollbar {
    display: none;
}

.kiosk-inbody-detail__personal {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    padding: 1rem 1rem;
    font-weight: 600;
    font-size: 1.4rem;
}

.kiosk-inbody-detail__comment {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    padding: 0 0 1rem 1rem;
    color: $gray;
    font-size: 0.9rem;
    font-weight: 600;
}

.kiosk-inbody-detail--container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(8, minmax(100px, auto));
}

.kiosk-inbody-detail__subtitle {
    color: $kiosk-primary;
    font-size: 1.5rem;
    font-weight: 600;
}

// bar graph canvas
.kiosk-inbody-detail canvas {
    width: 100%;
    height: 100%;
}

.kiosk-inbody-detail__composition {
    grid-column: 1 / 3;
    grid-row: 1 / 4;
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: auto minmax(0, 1fr);
    width: 100%;
    height: 100%;
    padding: 1rem;
}

.kiosk-inbody-detail__composition--container {
    display: grid;
    grid-template-columns: minmax(0, 1fr) 100px;
    grid-template-rows: repeat(5, minmax(0, 1fr));
    align-items: center;
    width: 100%;
    height: 100%;
    padding: 1rem 0;
}

.kiosk-inbody-detail__compositoin__label {
    display: flex;
    justify-content: space-between;
    padding-right: 1rem;

    span:nth-child(2) {
        font-weight: 600;
    }
}

.kiosk-inbody-detail__muscle-fat {
    grid-column: 1 / 3;
    grid-row: 4 / 7;
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: auto minmax(0, 1fr);
    width: 100%;
    height: 100%;
    padding: 0 1rem 1rem 1rem;
}

.kiosk-inbody-detail__muscle-fat--container {
    display: grid;
    grid-template-columns: auto minmax(0, 1fr);
    grid-template-rows: repeat(3, minmax(0, 1fr));
    width: 100%;
    height: 100%;
    padding: 1rem 0;
}

.kiosk-inbody-detail__obesity {
    grid-column: 1 / 3;
    grid-row: 7 / 9;
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: auto minmax(0, 1fr);
    width: 100%;
    height: 100%;
    padding: 0 1rem 1rem 1rem;
}

.kiosk-inbody-detail__obesity--container {
    display: grid;
    grid-template-columns: auto minmax(0, 1fr);
    grid-template-rows: repeat(2, minmax(0, 1fr));
    width: 100%;
    height: 100%;
}

.kiosk-inbody-detail__bar-label {
    align-self: center;
    font-weight: 600;
    text-align: center;
    line-height: 130%;
}

.kiosk-inbody-detail__total {
    grid-column: 3 / 4;
    grid-row: 1 / 9;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    width: 100%;
    height: 100%;
    padding: 1rem;
}

.kiosk-inbody-detail__total__score {
    font-size: 5rem;
}

.kiosk-inbody-detail__total__label {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    span:nth-child(1) {
        font-weight: 600;
    }
}
</style>
