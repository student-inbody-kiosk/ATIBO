<script setup lang="ts">
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import KioGymDetail from '@/components/kiosk/gym/KioGymDetail.vue';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// Update kio-header
onMounted(() => {
    emit('update-header', {
        routeName: 'kiosk-gym',
        routeParams: {},
        routeQuery: {},
    });
});

const handleGetGymName = function updateHeaderName(name: string) {
    emit('update-header', {
        title: name,
    });
};

// Get gymId from URL
const route = useRoute();
const gymId = Number(route.params.gymId);
</script>

<template>
    <div class="kisok-gym-detail-view">
        <KioGymDetail :gymId="gymId" @get-gym-name="handleGetGymName" />
    </div>
</template>

<style lang="scss">
.kisok-gym-detail-view {
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: minmax(0, 1fr);
    height: 100%;
    padding: 1rem;
}
</style>
