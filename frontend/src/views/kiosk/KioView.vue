<script setup lang="ts">
import { RouterView } from 'vue-router';
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
</script>

<template>
    <KioLayout>
        <template #kiosk-header> </template>
        <template #kiosk-main>
            <router-view />
        </template>
    </KioLayout>
</template>

<style lang="scss" scoped></style>
