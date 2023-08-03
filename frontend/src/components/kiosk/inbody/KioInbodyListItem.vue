<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';

defineProps<{
    inbody: InbodyDetail;
}>();
</script>

<template lang="">
    <RouterLink
        :to="{ name: 'kiosk-inbody-detail', params: { inbodyId: inbody.id } }">
        <div class="kiosk-inbody-list-item" @click="handleClick">
            <p>{{ inbody.testDate }}</p>
            <p>{{ inbody.weight }}kg</p>
            <p>{{ inbody.percentBodyFat }}%</p>
            <p>{{ inbody.skeletalMuscleMass }}kg</p>
        </div>
    </RouterLink>
</template>

<style lang="scss">
.kiosk-inbody-list-item {
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
