<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';
import KioInbodyListItem from '@/components/kiosk/inbody/KioInbodyListItem.vue';

defineProps<{
    inbodys: InbodyDetail[];
}>();
</script>

<template>
    <div class="kiosk-inbody-list--container">
        <li class="kiosk-inbody-list">
            <KioInbodyListItem
                v-for="inbody in inbodys"
                :key="inbody.id"
                :inbody="inbody">
            </KioInbodyListItem>
        </li>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list--container {
    height: 100%;
    padding: 0 0.4rem 0 0;
    border: $kiosk-primary 5px solid;
    border-radius: 1rem;
}

.kiosk-inbody-list {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    overflow-y: auto;
    height: 100%;
    padding: 1rem 0.6rem 1rem 1rem;
}
</style>
