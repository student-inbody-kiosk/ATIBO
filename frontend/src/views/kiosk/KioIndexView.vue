<script setup lang="ts">
import KioLayout from '@/components/kiosk/KioLayout.vue';
import IconButton from '@/components/common/IconButton.vue';
import TheButton from '@/components/common/TheButton.vue';
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <IconButton
                text="관리자"
                emitMessage="admin"
                @admin="$router.push({ name: 'admin' })">
                <template #icon>
                    <font-awesome-icon icon="user-lock" />
                </template>
            </IconButton>
        </template>
        <template #kiosk-main>
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
                @attend="$router.push({ name: 'kiosk-attend' })" />

            <TheButton
                text="운동기구"
                color="kiosk-primary"
                size="lg"
                emitMessage="attend"
                @attend="$router.push({ name: 'kiosk-attend' })" />
        </template>
    </KioLayout>
</template>

<style lang="scss" scoped></style>
