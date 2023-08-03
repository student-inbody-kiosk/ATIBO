<script setup lang="ts">
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import services from '@/apis/services';
import KioGymDetail from '@/components/kiosk/gym/KioGymDetail.vue';
import type { HeaderUpdate } from '@/types/app.interface';

const emit = defineEmits<{
    (e: 'update-header', info: HeaderUpdate): void;
}>();

const route = useRoute();
const gymId = Number(route.params.gymId);

// gym detail
const gym = await services.getGym(gymId);

// update kio-header
onMounted(() => {
    emit('update-header', {
        title: gym.name,
        routeName: 'kiosk-gym',
        routeParams: {},
        routeQuery: {},
    });
});
</script>

<template>
    <div class="kisok-gym-detail-view">
        <KioGymDetail :gym="gym" />
    </div>
</template>

<style lang="scss">
.kisok-gym-detail-view {
    padding: 1rem;
    height: 100%;
}
</style>
