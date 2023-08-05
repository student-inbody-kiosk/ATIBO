<script setup lang="ts">
import { onBeforeMount } from 'vue';
import KioGymList from '@/components/kiosk/gym/KioGymList.vue';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// update kio-header
onBeforeMount(() => {
    emit('update-header', {
        title: '운동기구',
        routeName: 'kiosk-index',
        routeParams: {},
        routeQuery: {},
    });
});
</script>

<template>
    <div class="kiosk-gym-view">
        <KioGymList />
    </div>
</template>

<style lang="scss">
.kiosk-gym-view {
    width: 100%;
    height: 100%;
}
</style>
