<script setup lang="ts">
import GymUpdateDescription from '@/components/admin/gym/GymUpdateDescription.vue';
import GymUpdateImage from '@/components/admin/gym/GymUpdateImage.vue';

const props = defineProps<{
    gymId: number;
}>();
</script>

<template>
    <article class="gym-update">
        <GymUpdateDescription :gymId="gymId" />
        <GymUpdateImage :gymId="gymId" />
    </article>
</template>

<style lang="scss">
@import '@/styles/ckeditor.scss';

.gym-update {
    display: grid;
    grid-template-columns: minmax(0, 1fr) minmax(200px, 300px);
    grid-template-rows: minmax(0, 1fr);
    column-gap: 1rem;
    width: 100%;
    min-width: 800px;
    height: 100%;
}
</style>
