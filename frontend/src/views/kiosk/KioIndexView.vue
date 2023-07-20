<script setup lang="ts">
import KioHeader from '@/components/kiosk/common/KioHeader.vue';
import KioLayout from '@/components/kiosk/common/KioLayout.vue';
</script>

<template>
    <KioLayout>
        <template #header>
            <KioHeader />
        </template>
        <template #main> </template>
    </KioLayout>
</template>

<style lang="scss">
body {
    background-color: $kiosk-secondary;
}
</style>
