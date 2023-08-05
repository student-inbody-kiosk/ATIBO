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
    grid-template-columns: minmax(0, 1fr) 300px;
    grid-template-rows: minmax(0, 1fr);
    width: 100%;
    height: 100%;
}

.gym-update::-webkit-scrollbar {
    display: none;
}
</style>
