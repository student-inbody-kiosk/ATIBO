<script setup lang="ts">
import KioLayout from '@/components/kiosk/KioLayout.vue';
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <font-awesome-icon
                icon="user-lock"
                @click="$router.push({ name: 'admin' })" />
        </template>
        <template #kiosk-main>
            <button @click="$router.push({ name: 'kiosk-attend' })">
                출석 확인
            </button>
            <button @click="$router.push({ name: 'kiosk-inbody' })">
                인바디
            </button>
            <button @click="$router.push({ name: 'kiosk-gym' })">
                운동기구
            </button>
        </template>
    </KioLayout>
</template>

<style lang="scss">
body {
    background-color: $kiosk-secondary;
}
</style>
