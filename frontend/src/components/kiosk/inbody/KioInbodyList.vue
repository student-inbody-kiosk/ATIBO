<script setup lang="ts">
import type { InbodyDetail } from '@/types/inbody.interface';
import KioInbodyListLabel from '@/components/kiosk/inbody/KioInbodyListLabel.vue';
import KioInbodyListItem from '@/components/kiosk/inbody/KioInbodyListItem.vue';

defineProps<{
    inbodys: InbodyDetail[];
}>();
</script>

<template>
    <div class="kiosk-inbody-list">
        <KioInbodyListLabel />
        <ul class="kiosk-inbody-list__container">
            <li class="kiosk-inbody-list__list">
                <KioInbodyListItem
                    v-for="inbody in inbodys"
                    :key="inbody.id"
                    :inbody="inbody">
                </KioInbodyListItem>
            </li>
        </ul>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-list {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr);
    height: 100%;
    border: $kiosk-primary 5px solid;
    border-radius: 1rem;
}

.kiosk-inbody-list__container {
    padding: 0 0.4rem 0 0;
}

.kiosk-inbody-list__list {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    overflow-y: auto;
    height: 100%;
    padding: 1rem 0.6rem 1rem 1rem;
}
</style>
