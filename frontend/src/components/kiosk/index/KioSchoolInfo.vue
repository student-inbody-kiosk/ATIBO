<script setup lang="ts">
import services from '@/apis/services';

let schoolInfo = await services.getSchoolInfo();
</script>

<template>
    <div class="kiosk-school-info">
        <img class="kiosk-school-info__logo" :src="schoolInfo?.logoImage" />
        <h1 class="kiosk-school-info__name">{{ schoolInfo?.name }}</h1>
    </div>
</template>

<style lang="scss" scoped>
.kiosk-school-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
}

.kiosk-school-info__logo {
    width: auto;
    height: 10rem;
}

.kiosk-school-info__name {
    font-size: 4rem;
    font-weight: 700;
}
</style>
