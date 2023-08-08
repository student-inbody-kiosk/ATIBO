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

const props = defineProps<{
    name: string;
    sex: number;
    inbody: InbodyDetail;
}>();

/* InbodyDetail -> chartJS barGraph dataset */
const barData = computed(() => {
    return inbodyToBarData(props.inbody);
});

/* chartJS setting */
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip);

// Calculate personal avg, min, max data
const avgValue = getAverageValue(props.inbody, props.sex ? props.sex : 1);
const minValue = getMinValue(props.inbody, props.sex ? props.sex : 1);
const maxValue = getMaxValue(props.inbody, props.sex ? props.sex : 1);

// Set the caculated data to the graph
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
    <div class="admin-inbody-detail">
        <div class="admin-inbody-detail__personal">
            <p>이름: {{ name }}</p>
            <p>나이: {{ inbody.age }}</p>
            <p>키: {{ inbody.height }}cm</p>
            <p>성별: {{ sex === 1 ? '남' : '여' }}</p>
            <p>날짜: {{ inbody.testDate }}</p>
        </div>
        <div class="admin-inbody-detail__comment">
            <p>* 본 결과지는 간이 결과지입니다.</p>
            <p>
                * 키를 임력하지 않은 경우 평균 키로 계산됩니다. (남: 173.7cm,
                여: 160.9cm)
            </p>
        </div>
        <div class="admin-inbody-detail--container">
            <section class="admin-inbody-detail__total">
                <p class="admin-inbody-detail__total__label">
                    <span>인바디 점수</span
                    ><span class="admin-inbody-detail__total__score">
                        {{ inbody.score }}</span
                    >
                </p>
                <p class="admin-inbody-detail__total__label">
                    <span>적정 체중</span
                    ><span> {{ avgValue.weight.toFixed(2) }} kg</span>
                </p>
                <p class="admin-inbody-detail__total__label">
                    <span>체중 조절</span
                    ><span>
                        {{ (avgValue.weight - inbody.weight).toFixed(2) }}
                        kg</span
                    >
                </p>
                <p class="admin-inbody-detail__total__label">
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
                <p class="admin-inbody-detail__total__label">
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
                <p class="admin-inbody-detail__total__label">
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
                <p class="admin-inbody-detail__total__label">
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
            </section>
            <section class="admin-inbody-detail__composition">
                <h2 class="admin-inbody-detail__subtitle">체성분 분석</h2>
                <div class="admin-inbody-detail__composition--container">
                    <div class="admin-inbody-detail__compositoin__label">
                        <div>
                            <p>우리 몸을 이루는 물</p>
                            <p>체수분</p>
                        </div>
                        <p>{{ inbody.totalBodyWater }} L</p>
                    </div>

                    <div class="admin-inbody-detail__compositoin__label">
                        <div>
                            <p>근육을 만들어 주는</p>
                            <p>단백질</p>
                        </div>
                        <p>{{ inbody.protein }} kg</p>
                    </div>

                    <div class="admin-inbody-detail__compositoin__label">
                        <div>
                            <p>뼈를 단단하게 하는</p>
                            <p>무기질</p>
                        </div>
                        <p>{{ inbody.minerals }} kg</p>
                    </div>

                    <div class="admin-inbody-detail__compositoin__label">
                        <div>
                            <p>남은 에너지를 저장한</p>
                            <p>체지방</p>
                        </div>
                        <p>{{ inbody.bodyFatMass }} kg</p>
                    </div>

                    <div class="admin-inbody-detail__compositoin__label">
                        <div>
                            <p>위의 모든 값을 합한</p>
                            <p>체중</p>
                        </div>
                        <p>{{ inbody.weight }} kg</p>
                    </div>
                </div>
            </section>
            <section class="admin-inbody-detail__muscle-fat">
                <h2 class="admin-inbody-detail__subtitle">골격근 지방 분석</h2>
                <div class="admin-inbody-detail__muscle-fat--container">
                    <p class="admin-inbody-detail__bar-label">
                        체중<br />
                        {{ inbody.weight }} kg
                    </p>
                    <div>
                        <Bar
                            :data="barData.weightData"
                            :options="weightOptions" />
                    </div>
                    <p class="admin-inbody-detail__bar-label">
                        골격근량 <br />
                        {{ inbody.skeletalMuscleMass }} kg
                    </p>
                    <div>
                        <Bar
                            :data="barData.skeletalMuscleMassData"
                            :options="skeletalMuscleMassOptions" />
                    </div>
                    <p class="admin-inbody-detail__bar-label">
                        체지방량<br />
                        {{ inbody.bodyFatMass }} kg
                    </p>
                    <div>
                        <Bar
                            :data="barData.bodyFatMassData"
                            :options="bodyFatMassOptions" />
                    </div>
                </div>
            </section>
            <section class="admin-inbody-detail__obesity">
                <h2 class="admin-inbody-detail__subtitle">비만 분석</h2>
                <div class="admin-inbody-detail__obesity--container">
                    <p class="admin-inbody-detail__bar-label">
                        BMI<br />
                        {{ inbody.bodyMassIndex }}
                    </p>
                    <div>
                        <Bar
                            :data="barData.bodyMassIndexData"
                            :options="bodyMassIndexOptions" />
                    </div>
                    <p class="admin-inbody-detail__bar-label">
                        체지방률<br />
                        {{ inbody.percentBodyFat }} %
                    </p>
                    <div>
                        <Bar
                            :data="barData.percentBodyFatData"
                            :options="percentBodyFatOptions" />
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.admin-inbody-detail {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    min-width: 45rem;
    width: 100%;
    padding: 1rem;
    background-color: white;
    border-radius: 0.5rem;
    overflow: auto;
    font-size: 1.2rem;
    font-weight: 500;
}

// .admin-inbody-detail::-webkit-scrollbar {
//     display: none;
// }

.admin-inbody-detail__personal {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding: 1rem;
    font-weight: 600;
    font-size: 1.4rem;
}

.admin-inbody-detail__comment {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    padding: 0 0 1rem 1rem;
    color: $gray;
    font-size: 0.9rem;
    font-weight: 600;
}

.admin-inbody-detail--container {
    display: grid;
    gap: 3rem;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: 1fr;
}

.admin-inbody-detail__subtitle {
    color: $admin-primary;
    font-size: 1.5rem;
    font-weight: 600;
    text-align: center;
    padding-bottom: 2rem;
}

// bar graph canvas
.admin-inbody-detail canvas {
    // width: 100%;
    // height: 100%;
}

.admin-inbody-detail__composition {
    // display: flex;
    // flex-direction: column;
    // align-content: space-around;
    // grid-column: 1 / 3;
    // grid-row: 1 / 4;
    // display: grid;
    // grid-template-columns: minmax(0, 1fr);
    // grid-template-rows: auto minmax(0, 1fr);
    // width: 100%;
    // height: 100%;
    // padding: 1rem;
}

.admin-inbody-detail__composition--container {
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: repeat(5, minmax(0, 1fr));

    height: 70%;
    padding: 1rem 0;
}

.admin-inbody-detail__compositoin__label {
    display: grid;
    grid-template-columns: auto minmax(0, 1fr);
    grid-template-rows: repeat(2, minmax(0, 1fr));
    gap: 1.5rem;
    align-items: center;

    div {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 0.5rem;

        p:first-child {
            font-size: 1rem;
        }

        p:last-child {
            font-size: 1.3rem;
            font-weight: 600;
        }
    }
}

.admin-inbody-detail__muscle-fat {
    // grid-column: 1 / 3;
    // grid-row: 4 / 7;
    // display: grid;
    // grid-template-columns: minmax(0, 1fr);
    // grid-template-rows: auto minmax(0, 1fr);
    // width: 100%;
    height: 100%;
    // padding: 0 1rem 1rem 1rem;
}

.admin-inbody-detail__muscle-fat--container {
    // display: grid;
    // grid-template-columns: auto minmax(0, 1fr);
    // grid-template-rows: repeat(3, minmax(0, 1fr));
    // width: 100%;
    // height: 100%;
    // padding: 1rem 0;
}

.admin-inbody-detail__obesity {
    // grid-column: 1 / 3;
    // grid-row: 7 / 9;
    // display: grid;
    // grid-template-columns: minmax(0, 1fr);
    // grid-template-rows: auto minmax(0, 1fr);
    // width: 100%;
    // height: 100%;
    // padding: 0 1rem 1rem 1rem;
}

.admin-inbody-detail__obesity--container {
    // display: grid;
    // grid-template-columns: auto minmax(0, 1fr);
    // grid-template-rows: repeat(2, minmax(0, 1fr));
    // width: 100%;
    // height: 100%;
}

.admin-inbody-detail__bar-label {
    align-self: center;
    font-weight: 600;
    text-align: center;
    line-height: 130%;
}

.admin-inbody-detail__total {
    // grid-column: 3 / 4;
    // grid-row: 1 / 9;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    // width: 100%;
    height: 100%;
    padding: 1rem;
}

.admin-inbody-detail__total__score {
    font-size: 5rem;
}

.admin-inbody-detail__total__label {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    span:nth-child(1) {
        font-weight: 600;
    }
}
</style>
