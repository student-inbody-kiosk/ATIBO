<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
</script>

<template>
    <div class="kiosk-index__buttons">
        <TheButton
            text="출석 확인"
            color="kiosk-primary"
            size="lg"
            @click="$router.push({ name: 'kiosk-attend' })" />
        <TheButton
            text="인바디"
            color="kiosk-primary"
            size="lg"
            emitMessage="attend"
            @click="$router.push({ name: 'kiosk-inbody' })" />

        <TheButton
            text="운동기구"
            color="kiosk-primary"
            size="lg"
            emitMessage="attend"
            @click="$router.push({ name: 'kiosk-gym' })" />
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
