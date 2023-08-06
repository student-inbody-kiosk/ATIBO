<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';
defineProps<{
    index: number;
    inbody: InbodyDetail;
}>();
</script>

<template>
    <tr>
        <td>{{ index + 1 }}</td>
        <td>
            {{ inbody.testDate }}
        </td>
        <td>
            <span>{{ inbody.score }} </span> 점
        </td>
        <td>
            <span>{{ inbody.age }} </span> 세
        </td>
        <td>
            <span>{{ inbody.height }}</span> cm
        </td>
        <td>
            <span> {{ inbody.weight }} </span> g
        </td>
        <td>
            <span>{{ inbody.percentBodyFat }} </span> %
        </td>
        <td>
            <span> {{ inbody.skeletalMuscleMass }}</span> kg
        </td>
        <td class="inbody-data-label__content">
            <span>{{ inbody.bodyFatMass }}</span>
        </td>
        <td>
            <span> {{ inbody.bodyMassIndex }}</span> kg
        </td>
        <td>
            <span>{{ inbody.totalBodyWater }}</span> L
        </td>
        <td>
            <span>{{ inbody.protein }}</span> kg
        </td>
        <td>
            <span>{{ inbody.minerals }}</span> kg
        </td>
    </tr>
</template>

<style lang="scss" scoped>
tr {
    background-color: $white;
}

td {
    display: block;
    min-width: 9rem;
    padding: 0.6rem;
    border: 0.1rem solid $admin-secondary;
    font-size: 1.1rem;
    text-align: center;
}

span {
    font-size: 1.1rem;
    font-weight: 600;
}
</style>
