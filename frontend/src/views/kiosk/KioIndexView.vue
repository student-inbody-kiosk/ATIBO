<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
</script>

<template>
    <div class="kiosk-main_buttons">
        <TheButton
            text="출석 확인"
            color="kiosk-primary"
            size="lg"
            emitMessage="attend"
            @attend="$router.push({ name: 'kiosk-attend' })" />
        <TheButton
            text="인바디"
            color="kiosk-primary"
            size="lg"
            emitMessage="attend"
            @attend="$router.push({ name: 'kiosk-inbody' })" />

        <TheButton
            text="운동기구"
            color="kiosk-primary"
            size="lg"
            emitMessage="attend"
            @attend="$router.push({ name: 'kiosk-gym' })" />
    </div>
</template>

<style lang="scss" scoped></style>
