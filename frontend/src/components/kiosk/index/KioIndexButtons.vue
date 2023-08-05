<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';
</script>

<template>
    <div class="kiosk-index__buttons">
        <RouterLink :to="{ name: 'kiosk-attend' }">
            <VButton text="출석 확인" color="kiosk-primary" size="xl" />
        </RouterLink>
        <RouterLink :to="{ name: 'kiosk-inbody' }">
            <VButton text="인바디" color="kiosk-primary" size="xl" />
        </RouterLink>
        <RouterLink :to="{ name: 'kiosk-gym' }">
            <VButton text="운동기구" color="kiosk-primary" size="xl" />
        </RouterLink>
    </div>
</template>

<style lang="scss">
.kiosk-index__buttons {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 3rem;
}
</style>
