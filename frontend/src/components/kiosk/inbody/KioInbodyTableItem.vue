<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';

defineProps<{
    inbody: InbodyDetail;
}>();
</script>

<template lang="">
    <RouterLink
        class="kiosk-inbody-table-item--anchor"
        :to="{ name: 'kiosk-inbody-detail', params: { inbodyId: inbody.id } }">
        <div class="kiosk-inbody-table-item" @click="handleClick">
            <p>{{ inbody.testDate }}</p>
            <p>{{ inbody.weight }}kg</p>
            <p>{{ inbody.percentBodyFat }}%</p>
            <p>{{ inbody.skeletalMuscleMass }}kg</p>
        </div>
    </RouterLink>
</template>

<style lang="scss">
.kiosk-inbody-table-item {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    justify-items: center;
    padding: 1rem;
    background-color: $white;
    border-radius: 0.5em;
    color: inherit;
    font-size: 1.3rem;

    p:nth-child(1) {
        font-weight: 600;
    }
}

.kiosk-inbody-table-item--anchor {
    color: inherit;
    text-decoration: none;
}
</style>
