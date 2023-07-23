<script setup lang="ts">
import KioHeader from '@/components/kiosk/KioHeader.vue';
import KioLayout from '@/components/kiosk/KioLayout.vue';
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <KioHeader title="운동기구" backUrl="kiosk" />
        </template>
        <template #kiosk-main> </template>
    </KioLayout>
</template>

<style lang="scss"></style>
