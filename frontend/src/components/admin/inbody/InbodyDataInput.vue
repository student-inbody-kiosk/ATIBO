<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import type { InbodyDetail } from '@/types/inbody.interface';

withDefaults(
    defineProps<{
        index: number;
        inbody: InbodyDetail;
        isCreate?: boolean;
    }>(),
    { isCreate: false }
);

defineEmits<{
    (e: 'input', index: number, item: string, value: string): void;
}>();
</script>

<template>
    <tr>
        <td>
            {{ index + 1 }}
        </td>
        <td>
            <VInput
                id="testDate"
                type="date"
                :value="inbody.testDate"
                @input="
                    (testDate) => $emit('input', index, 'testDate', testDate)
                " />
        </td>
        <td>
            <VInput
                id="score"
                :value="inbody.score"
                @input="
                    (score) => $emit('input', index, 'score', Number(score))
                " />
        </td>
        <td>
            <VInput
                id="age"
                :value="inbody.age"
                @input="(age) => $emit('input', index, 'age', Number(age))" />
        </td>
        <td>
            <VInput
                id="height"
                :value="inbody.height"
                @input="
                    (height) => $emit('input', index, 'height', Number(height))
                " />
        </td>
        <td>
            <VInput
                id="weight"
                :value="inbody.weight"
                @input="
                    (weight) => $emit('input', index, 'weight', Number(weight))
                " />
        </td>
        <td>
            <VInput
                id="percentBodyFat"
                :value="inbody.percentBodyFat"
                @input="
                    (percentBodyFat) =>
                        $emit(
                            'input',
                            index,
                            'percentBodyFat',
                            Number(percentBodyFat)
                        )
                " />
        </td>
        <td>
            <VInput
                id="skeletalMuscleMass"
                :value="inbody.skeletalMuscleMass"
                @input="
                    (skeletalMuscleMass) =>
                        $emit(
                            'input',
                            index,
                            'skeletalMuscleMass',
                            Number(skeletalMuscleMass)
                        )
                " />
        </td>
        <td>
            <VInput
                id="bodyFatMass"
                :value="inbody.bodyFatMass"
                @input="
                    (bodyFatMass) =>
                        $emit(
                            'input',
                            index,
                            'bodyFatMass',
                            Number(bodyFatMass)
                        )
                " />
        </td>
        <td>
            <VInput
                id="bodyMassIndex"
                :value="inbody.bodyMassIndex"
                @input="
                    (bodyMassIndex) =>
                        $emit(
                            'input',
                            index,
                            'bodyMassIndex',
                            Number(bodyMassIndex)
                        )
                " />
        </td>
        <td>
            <VInput
                id="totalBodyWater"
                :value="inbody.totalBodyWater"
                @input="
                    (totalBodyWater) =>
                        $emit(
                            'input',
                            index,
                            'totalBodyWater',
                            Number(totalBodyWater)
                        )
                " />
        </td>
        <td>
            <VInput
                id="protein"
                :value="inbody.protein"
                @input="
                    (protein) =>
                        $emit('input', index, 'protein', Number(protein))
                " />
        </td>
        <td>
            <VInput
                id="minerals"
                :value="inbody.minerals"
                @input="
                    (minerals) =>
                        $emit('input', index, 'minerals', Number(minerals))
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
    min-height: 2.425rem;
    padding: 0.3rem;
    border: 0.1rem solid $admin-secondary;
    font-size: 1.1rem;
    text-align: center;
}
</style>
