<script setup lang="ts">
import { RouterView } from 'vue-router';
import KioLayout from '@/components/kiosk/KioLayout.vue';
import KioHeader from '@/components/kiosk/KioHeader.vue';
import IconButton from '@/components/common/IconButton.vue';

import { useRoute } from 'vue-router';
import { ref, watchEffect } from 'vue';

const route = useRoute();
const isIndexPage = ref(false);
const title = ref('');
const routeName = ref('');

const handleBeforeMount = function allocateTitleAndRouteName(data: {
    title: string;
    routeName: string;
}) {
    title.value = data.title;
    routeName.value = data.routeName;
};

watchEffect(() => {
    if (route.name === 'kiosk') {
        isIndexPage.value = true;
    } else {
        isIndexPage.value = false;
    }
});
</script>

<template>
    <KioLayout>
        <template #kiosk-header>
            <IconButton
                v-show="isIndexPage"
                text="관리자"
                emitMessage="admin"
                @admin="$router.push({ name: 'admin-index' })">
                <template #icon>
                    <font-awesome-icon icon="user-lock" />
                </template>
            </IconButton>
            <KioHeader
                v-show="!isIndexPage"
                :title="title"
                :routeName="routeName" />
        </template>
        <template #kiosk-main>
            <router-view @before-mount="handleBeforeMount" />
        </template>
    </KioLayout>
</template>

<style lang="scss" scoped></style>
