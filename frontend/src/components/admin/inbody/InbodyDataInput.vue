<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import type { InbodyDetail } from '@/types/inbody.interface';

defineProps<{
    inbody: InbodyDetail;
}>();

defineEmits<{
    (e: 'input', key: string, value: string): void;
}>();

const maxDate = new Date().toISOString().split('T')[0];
</script>

<template>
    <tr>
        <td>
            <VInput
                id="testDate"
                type="date"
                :max="maxDate"
                size="xs"
                :value="inbody.testDate"
                @input="(testDate) => $emit('input', 'testDate', testDate)" />
        </td>
        <td>
            <VInput
                id="score"
                size="xs"
                :value="inbody.score"
                @input="(score) => $emit('input', 'score', Number(score))" />
        </td>
        <td>
            <VInput
                id="age"
                size="xs"
                :value="inbody.age"
                @input="(age) => $emit('input', 'age', Number(age))" />
        </td>
        <td>
            <VInput
                id="height"
                size="xs"
                :value="inbody.height"
                @input="(height) => $emit('input', 'height', Number(height))" />
        </td>
        <td>
            <VInput
                id="weight"
                size="xs"
                :value="inbody.weight"
                @input="(weight) => $emit('input', 'weight', Number(weight))" />
        </td>
        <td>
            <VInput
                id="percentBodyFat"
                size="xs"
                :value="inbody.percentBodyFat"
                @input="
                    (percentBodyFat) =>
                        $emit('input', 'percentBodyFat', Number(percentBodyFat))
                " />
        </td>
        <td>
            <VInput
                id="skeletalMuscleMass"
                size="xs"
                :value="inbody.skeletalMuscleMass"
                @input="
                    (skeletalMuscleMass) =>
                        $emit(
                            'input',
                            'skeletalMuscleMass',
                            Number(skeletalMuscleMass)
                        )
                " />
        </td>
        <td>
            <VInput
                id="bodyFatMass"
                size="xs"
                :value="inbody.bodyFatMass"
                @input="
                    (bodyFatMass) =>
                        $emit('input', 'bodyFatMass', Number(bodyFatMass))
                " />
        </td>
        <td>
            <VInput
                id="bodyMassIndex"
                size="xs"
                :value="inbody.bodyMassIndex"
                @input="
                    (bodyMassIndex) =>
                        $emit('input', 'bodyMassIndex', Number(bodyMassIndex))
                " />
        </td>
        <td>
            <VInput
                id="totalBodyWater"
                size="xs"
                :value="inbody.totalBodyWater"
                @input="
                    (totalBodyWater) =>
                        $emit('input', 'totalBodyWater', Number(totalBodyWater))
                " />
        </td>
        <td>
            <VInput
                id="protein"
                size="xs"
                :value="inbody.protein"
                @input="
                    (protein) => $emit('input', 'protein', Number(protein))
                " />
        </td>
        <td>
            <VInput
                id="minerals"
                size="xs"
                :value="inbody.minerals"
                @input="
                    (minerals) => $emit('input', 'minerals', Number(minerals))
                " />
        </td>
    </tr>
</template>

<style lang="scss" scoped>
tr {
    background-color: $white;
}

td {
    display: block;
    min-height: 2.5rem;
    padding: 0.3rem;
    border: 0.1rem solid $admin-secondary;
    font-size: 1.1rem;
    text-align: center;
}
</style>
