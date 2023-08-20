<script setup lang="ts">
import { RouterView } from 'vue-router';
import { useMeta } from 'vue-meta';

useMeta({
    title: 'ATIBO 아티보 키오스크',
    description: 'ATIBO 아티보 출결, 인바디 관리 키오스크',
});
</script>

<template>
    <metainfo />
    <RouterView />
</template>
