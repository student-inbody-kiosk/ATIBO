<script setup lang="ts">
import TheButton from '@/components/common/TheButton.vue';
import { getAccountInfo } from '@/apis/services/accounts';

const handleClick = () => {
    getAccountInfo();
};
</script>

<template>
    <div class="admin-main__buttons">
        <button @click="handleClick">ACCOUNT INFO</button>
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
    </div>
</template>

<style lang="scss">
.admin-main__buttons {
    display: flex;
    flex-direction: column;
}
</style>
