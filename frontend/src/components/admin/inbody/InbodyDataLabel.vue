<script setup lang="ts"></script>

<template>
    <thead>
        <tr class="inbody-data-label">
            <th class="inbody-data-label__content">날짜</th>
            <th class="inbody-data-label__content">인바디 점수 (점)</th>
            <th class="inbody-data-label__content">나이 (세)</th>
            <th class="inbody-data-label__content">키 (cm)</th>
            <th class="inbody-data-label__content">체중 (kg)</th>
            <th class="inbody-data-label__content">체지방률 (%)</th>
            <th class="inbody-data-label__content">골격근량 (kg)</th>
            <th class="inbody-data-label__content">체지방량 (kg)</th>
            <th class="inbody-data-label__content">BMI</th>
            <th class="inbody-data-label__content">체수분 (L)</th>
            <th class="inbody-data-label__content">단백질 (kg)</th>
            <th class="inbody-data-label__content">무기질 (kg)</th>
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
