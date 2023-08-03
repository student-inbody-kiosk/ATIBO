<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';

defineProps<{
    inbody: InbodyDetail;
}>();
</script>

<template lang="">
    <div class="kiosk-inbody-detail">
        <p>{{ inbody.testDate }}</p>
        <p>{{ inbody.weight }}kg</p>
        <p>{{ inbody.percentBodyFat }}%</p>
        <p>{{ inbody.skeletalMuscleMass }}kg</p>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-detail {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    justify-items: center;
    padding: 1rem;
    background-color: $white;
    border-radius: 0.5em;
    font-size: 1.2rem;
    font-weight: 500;
}
</style>
