<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';

defineProps<{
    inbody: InbodyDetail;
}>();
</script>

<template lang="">
    <div class="kiosk-inbody-list-item">
        <p>{{ inbody.testDate }}</p>
        <p>{{ inbody.weight }}kg</p>
        <p>{{ inbody.percentBodyFat }}%</p>
        <p>{{ inbody.skeletalMuscleMass }}kg</p>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list-item {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    padding: 1rem;
    background-color: $white;
    border-radius: 0.5em;
}
</style>
