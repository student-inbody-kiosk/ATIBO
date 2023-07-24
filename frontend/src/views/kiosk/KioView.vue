<script setup lang="ts">
import { RouterView } from 'vue-router';
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';

import { ref } from 'vue';

const title = ref('');
const routeName = ref('');

const handleBeforeMount = function allocateTitleAndRouteName(data: {
    title: string;
    routeName: string;
}) {
    title.value = data.title;
    routeName.value = data.routeName;
};
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <KioHeader :title="title" :routeName="routeName" />
        </template>
        <template #kiosk-main>
            <router-view @before-mount="handleBeforeMount" />
        </template>
    </KioLayout>
</template>

<style lang="scss" scoped></style>
