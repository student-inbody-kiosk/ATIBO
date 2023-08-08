<script setup lang="ts">
import VButton from '@/components/common/VButton.vue';

import { useAccountsStore } from '@/stores/accounts.store';
const { username, role } = useAccountsStore();
</script>

<template>
    <div class="admin-main">
        <div class="admin-main__account">
            <span> 반갑습니다, {{ username }} 님! </span>
        </div>
        <section class="admin-main__buttons">
            <VButton
                text="학생 관리"
                color="admin-primary"
                size="xl"
                @click="$router.push({ name: 'admin-student' })" />
            <VButton
                text="출결 관리"
                color="admin-primary"
                size="xl"
                @click="$router.push({ name: 'admin-attend' })" />
            <VButton
                text="인바디 관리"
                color="admin-primary"
                size="xl"
                @click="$router.push({ name: 'admin-inbody' })" />
            <VButton
                v-if="role === 'admin'"
                text="운동기구 관리"
                color="admin-primary"
                size="xl"
                @click="$router.push({ name: 'admin-gym' })" />
            <VButton
                v-if="role === 'admin'"
                text="학교정보 관리"
                color="admin-primary"
                size="xl"
                @click="$router.push({ name: 'admin-school' })" />
        </section>
    </div>
</template>

<style lang="scss">
.admin-main {
    width: 100%;
    height: 100%;
}
.admin-main__account {
    text-align: left;
    font-size: 1.3rem;
    font-weight: 600;
}

.admin-main__buttons {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    gap: 2rem;
}
</style>
