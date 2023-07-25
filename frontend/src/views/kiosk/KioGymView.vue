<script setup lang="ts">
import { onBeforeMount } from 'vue';

const emit = defineEmits(['before-mount']);
onBeforeMount(() => {
    emit('before-mount', { title: '운동 기구', routeName: 'kiosk-index' });
});
</script>

<template>
    <div>GYM</div>
</template>

<style lang="scss"></style>
