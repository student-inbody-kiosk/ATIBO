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
            <table class="inbody-total-table">
                <thead>
                    <tr>
                        <th>인바디 점수</th>
                        <th>적정 체중</th>
                        <th>체중 조절</th>
                        <th>지방 조절</th>
                        <th>근육 조절</th>
                        <th>BMI</th>
                        <th>체지방률</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{{ inbody.score }}</td>
                        <td>{{ avgValue.weight.toFixed(2) }} kg</td>
                        <td>
                            {{ (avgValue.weight - inbody.weight).toFixed(2) }}
                            kg
                        </td>
                        <td>
                            {{
                                (
                                    avgValue.bodyFatMass - inbody.bodyFatMass
                                ).toFixed(2)
                            }}
                            kg
                        </td>
                        <td>
                            {{
                                (
                                    avgValue.skeletalMuscleMass -
                                    inbody.skeletalMuscleMass
                                ).toFixed(2)
                            }}
                            kg
                        </td>
                        <td>
                            {{
                                inbody.bodyMassIndex > avgValue.maxBodyMassIndex
                                    ? '과체중'
                                    : inbody.bodyMassIndex <
                                      avgValue.minBodyMassIndex
                                    ? '저체중'
                                    : '표준'
                            }}
                        </td>
                        <td>
                            {{
                                inbody.percentBodyFat >
                                avgValue.maxPercentBodyFat
                                    ? '표준 이상'
                                    : inbody.percentBodyFat <
                                      avgValue.minPercentBodyFat
                                    ? '표준 이하'
                                    : '표준'
                            }}
                        </td>
                    </tr>
                </tbody>
            </table>

            <table class="inbody-composition-table">
                <thead>
                    <tr>
                        <th>
                            <span>우리 몸을 이루는 물</span>
                            <span>체수분</span>
                        </th>

                        <th>
                            <span>근육을 만들어 주는</span>
                            <span>단백질</span>
                        </th>

                        <th>
                            <span>뼈를 단단하게 하는</span>
                            <span>무기질</span>
                        </th>
                        <th>
                            <span>남은 에너지를 저장한</span>
                            <span>체지방</span>
                        </th>
                        <th>
                            <span>위의 모든 값을 합한</span>
                            <span>체중</span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{{ inbody.totalBodyWater }} L</td>
                        <td>{{ inbody.protein }} kg</td>
                        <td>{{ inbody.minerals }} kg</td>
                        <td>{{ inbody.bodyFatMass }} kg</td>
                        <td>{{ inbody.weight }} kg</td>
                    </tr>
                </tbody>
            </table>

            <section class="admin-inbody-detail__graph">
                <div>
                    <h2>골격근 지방 분석</h2>

                    <p>
                        체중
                        {{ inbody.weight }} kg
                    </p>
                    <div class="bar-container">
                        <Bar
                            :data="barData.weightData"
                            :options="weightOptions" />
                    </div>

                    <p>
                        골격근량
                        {{ inbody.skeletalMuscleMass }} kg
                    </p>
                    <div class="bar-container">
                        <Bar
                            :data="barData.skeletalMuscleMassData"
                            :options="skeletalMuscleMassOptions" />
                    </div>

                    <p>
                        체지방량
                        {{ inbody.bodyFatMass }} kg
                    </p>
                    <div class="bar-container">
                        <Bar
                            :data="barData.bodyFatMassData"
                            :options="bodyFatMassOptions" />
                    </div>
                </div>

                <div>
                    <h2>비만 분석</h2>

                    <p>
                        BMI
                        {{ inbody.bodyMassIndex }}
                    </p>
                    <div class="bar-container">
                        <Bar
                            :data="barData.bodyMassIndexData"
                            :options="bodyMassIndexOptions" />
                    </div>

                    <p>
                        체지방률
                        {{ inbody.percentBodyFat }} %
                    </p>
                    <div class="bar-container">
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
.admin-inbody-detail__personal {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-weight: 600;
    font-size: 1.4rem;
}

.admin-inbody-detail__comment {
    color: $gray-dark;
    font-size: 0.9rem;
    font-weight: 600;
    padding: 0.5rem 0;
    p {
        padding: 0.2rem 0;
    }
}

.admin-inbody-detail--container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: 1fr;
    gap: 1rem;
}

.inbody-total-table,
.inbody-composition-table {
    min-width: 10rem;
    display: flex;
    justify-content: space-evenly;
    font-size: 1.3rem;
    text-align: center;
    background-color: $white;
    border-radius: 0.5rem;
    thead {
        tr {
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;
        }
    }

    tbody {
        tr {
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;
        }
    }
}

.inbody-total-table {
    grid-column: 1/2;
}

.inbody-composition-table {
    grid-column: 2/3;
}

.inbody-composition-table {
    th {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;

        span:first-child {
            font-size: 1rem;
        }
    }
}

.admin-inbody-detail__graph {
    padding: 1rem;
    grid-column: 3/5;
    display: flex;
    flex-direction: column;
    background-color: $white;
    border-radius: 0.5rem;
    gap: 1rem;
    h2 {
        font-size: 1.5rem;
        text-align: center;
    }
}

.bar-container {
    width: 100%;
    min-width: 5rem;
    height: 4rem;
}
</style>
