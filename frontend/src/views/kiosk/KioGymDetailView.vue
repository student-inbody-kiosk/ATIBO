<script setup lang="ts">
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import KioGymDetail from '@/components/kiosk/gym/KioGymDetail.vue';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

// get gymId from URL
const route = useRoute();
const gymId = Number(route.params.gymId);

// update kio-header
onMounted(() => {
    emit('update-header', {
        routeName: 'kiosk-gym',
        routeParams: {},
        routeQuery: {},
    });
});

const handleGetGymName = function updateHeaderName(name: string) {
    console.log(1);
    emit('update-header', {
        title: name,
    });
};
</script>

<template>
    <div class="kisok-gym-detail-view">
        <KioGymDetail :gymId="gymId" @get-gym-name="handleGetGymName" />
    </div>
</template>

<style lang="scss">
.kisok-gym-detail-view {
    padding: 1rem;
    height: 100%;
}
</style>
