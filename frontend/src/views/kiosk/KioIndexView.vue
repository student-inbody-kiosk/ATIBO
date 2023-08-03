<script setup lang="ts">
import KioSchoolInfo from '@/components/kiosk/index/KioSchoolInfo.vue';
import KioIndexButtons from '@/components/kiosk/index/KioIndexButtons.vue';
import { ref } from 'vue';
import services from '@/apis/services';

const name = ref('');
const logoImage = ref(null);

const update = () => {
    // console.log(logoImage.value?.files[0]);
    services.updateSchoolInfo(name.value, logoImage.value?.files[0]);
};
</script>

<template>
    <div class="kiosk-index">
        <input type="text" v-model="name" />
        <input type="file" accept="image/*" ref="logoImage" />
        <button @click="update">clcick</button>
        <KioSchoolInfo />
        <KioIndexButtons />
    </div>
</template>

<style lang="scss">
.kiosk-index {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 1fr;
    padding-top: 8vh;
    height: 100%;
}
</style>
