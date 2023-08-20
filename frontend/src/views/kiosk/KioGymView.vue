<script setup lang="ts">
import { onBeforeMount } from 'vue';
import KioGymList from '@/components/kiosk/gym/KioGymList.vue';
import type { HeaderUpdate } from '@/types/app.interface';
import { useMeta } from 'vue-meta';

useMeta({
    title: 'ATIBO 아티보 운동기구 키오스크',
    description: 'ATIBO 아티보 운동기구 정보 조회 키오스크',
});

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
    padding: 1rem 2rem;
}
</style>
