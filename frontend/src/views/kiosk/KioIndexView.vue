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
    align-self: center;
    display: flex;
    flex-direction: column;
    gap: 4rem;
}
</style>
