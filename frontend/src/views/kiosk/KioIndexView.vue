<script setup lang="ts">
import KioSchoolInfo from '@/components/kiosk/index/KioSchoolInfo.vue';
import KioIndexButtons from '@/components/kiosk/index/KioIndexButtons.vue';
</script>

<template>
    <div class="kiosk-index">
        <KioSchoolInfo />
        <KioIndexButtons />
    </div>
</template>

<style lang="scss">
.kiosk-index {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 1fr;
    padding-top: 8vh;
    height: 100%;
}
</style>
