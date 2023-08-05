<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';
defineProps<{
    index: number;
    inbody: InbodyDetail;
}>();
</script>

<template>
    <tbody>
        <tr class="inbody-data-label">
            <td class="inbody-data-label__content">{{ index + 1 }}</td>
            <td class="inbody-data-label__content">{{ inbody.score }}</td>
            <td class="inbody-data-label__content">{{ inbody.age }}</td>
            <td class="inbody-data-label__content">{{ inbody.height }}</td>
            <td class="inbody-data-label__content">{{ inbody.weight }}</td>
            <td class="inbody-data-label__content">
                {{ inbody.percentBodyFat }}
            </td>
            <td class="inbody-data-label__content">
                {{ inbody.skeletalMuscleMass }}
            </td>
            <td class="inbody-data-label__content">{{ inbody.bodyFatMass }}</td>
            <td class="inbody-data-label__content">
                {{ inbody.bodyMassIndex }}
            </td>
            <td class="inbody-data-label__content">
                {{ inbody.totalBodyWater }}
            </td>
            <td class="inbody-data-label__content">{{ inbody.protein }}</td>
            <td class="inbody-data-label__content">{{ inbody.minerals }}</td>
        </tr>
    </tbody>
</template>

<style lang="scss" scoped>
.inbody-data-label {
    background-color: $white;
}

.inbody-data-label__content {
    display: block;
    min-width: 8rem;
    padding: 0.6rem;
    border: 0.1rem solid $admin-secondary;
    font-size: 1.2rem;
    text-align: center;
}
</style>
