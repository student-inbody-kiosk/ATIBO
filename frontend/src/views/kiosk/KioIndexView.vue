<script setup lang="ts">
import KioHeader from '@/components/kiosk/KioHeader.vue';
import KioLayout from '@/components/kiosk/KioLayout.vue';
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <KioHeader title="Title" leftUrl="kiosk" rightUrl="admin">
                <template #icon>
                    <font-awesome-icon icon="user-lock" /> </template
            ></KioHeader>
        </template>
        <template #kiosk-main> </template>
    </KioLayout>
</template>

<style lang="scss">
body {
    background-color: $kiosk-secondary;
}
</style>
