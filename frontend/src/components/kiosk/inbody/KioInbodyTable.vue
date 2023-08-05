<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';
import KioInbodyTableLabel from '@/components/kiosk/inbody/KioInbodyTableLabel.vue';
import KioInbodyTableItem from '@/components/kiosk/inbody/KioInbodyTableItem.vue';

defineProps<{
    inbodys: InbodyDetail[];
}>();
</script>

<template>
    <div class="kiosk-inbody-table">
        <KioInbodyTableLabel />
        <ul class="kiosk-inbody-table__container">
            <li class="kiosk-inbody-table__list">
                <KioInbodyTableItem
                    v-for="inbody in inbodys"
                    :key="inbody.id"
                    :inbody="inbody">
                </KioInbodyTableItem>
            </li>
        </ul>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-table {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    height: 100%;
    border: $kiosk-primary 5px solid;
    border-radius: 1rem;
}

.kiosk-inbody-table__container {
    padding: 0 0.4rem 0 0;
}

.kiosk-inbody-table__list {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    overflow-y: auto;
    height: 100%;
    padding: 1rem 0.6rem 1rem 1rem;
}
</style>
