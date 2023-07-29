<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';

import { useAccountsStore } from '@/stores/accounts.store';

const { username } = useAccountsStore();
</script>

<template>
    <div class="admin-main">
        <div class="admin-main__account">
            <span> 반갑습니다, {{ username }} 님! </span>
        </div>
        <section class="admin-main__buttons">
            <TheButton
                text="학생 관리"
                color="admin-primary"
                size="lg"
                emitMessage="student"
                @student="$router.push({ name: 'admin-student' })" />
            <TheButton
                text="출결 관리"
                color="admin-primary"
                size="lg"
                emitMessage="attend"
                @attend="$router.push({ name: 'admin-attend' })" />
            <TheButton
                text="인바디 관리"
                color="admin-primary"
                size="lg"
                emitMessage="inbody"
                @inbody="$router.push({ name: 'admin-inbody' })" />
        </section>
    </div>
</template>

<style lang="scss">
.admin-main {
    width: 100%;
    height: 100%;
}
.admin-main__account {
    text-align: right;
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
