<script setup lang="ts">
import services from '@/apis/services';

const schoolInfo = await services.getSchoolInfo();
</script>

<template>
    <div class="kiosk-school-info">
        <img
            class="kiosk-school-info__logo"
            :src="schoolInfo?.logoImage"
            alt="학교 로고" />
        <h1 class="kiosk-school-info__name">{{ schoolInfo?.name }}</h1>
    </div>
</template>

<style lang="scss" scoped>
.kiosk-school-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 2rem;
}

.kiosk-school-info__logo {
    width: auto;
    height: 20vh;
}

.kiosk-school-info__name {
    font-size: 5vh;
    font-weight: 700;
}
</style>
