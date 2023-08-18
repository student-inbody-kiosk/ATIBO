<script setup lang="ts"></script>

<template>
    <thead>
        <tr class="inbody-data-label">
            <th class="inbody-data-label__content" id="date">날짜</th>
            <th class="inbody-data-label__content" id="score">
                인바디 점수 (점)
            </th>
            <th class="inbody-data-label__content" id="age">나이 (세)</th>
            <th class="inbody-data-label__content" id="height">키 (cm)</th>
            <th class="inbody-data-label__content" id="weight">체중 (kg)</th>
            <th class="inbody-data-label__content" id="fatPercent">
                체지방률 (%)
            </th>
            <th class="inbody-data-label__content" id="muscle">
                골격근량 (kg)
            </th>
            <th class="inbody-data-label__content" id="fatMass">
                체지방량 (kg)
            </th>
            <th class="inbody-data-label__content" id="bmi">BMI</th>
            <th class="inbody-data-label__content" id="water">체수분 (L)</th>
            <th class="inbody-data-label__content" id="protein">단백질 (kg)</th>
            <th class="inbody-data-label__content" id="minerals">
                무기질 (kg)
            </th>
        </tr>
    </thead>
</template>

<style lang="scss" scoped>
thead {
    @include z-index(label);
    position: sticky;
    left: 0;
}
.inbody-data-label {
    background-color: $admin-tertiary;
}

.inbody-data-label__content {
    display: block;
    min-height: 2.5rem;
    min-width: 9rem;
    padding: 0.6rem;
    border: 0.1rem solid $admin-secondary;
    font-size: 1.1rem;
}
</style>
