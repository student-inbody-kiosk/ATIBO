<script setup lang="ts">
import services from '@/apis/services';
import { onBeforeMount } from 'vue';
import type { HeaderUpdate } from '@/types/app.interface';
import KioGymList from '@/components/kiosk/gym/KioGymList.vue';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// student info
const gyms = await services.getGyms();

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
    <div>
        <KioGymList :gyms="gyms" />
    </div>
</template>

<style lang="scss"></style>
