<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import { ref, onBeforeMount } from 'vue';

import services from '@/apis/services';

import type { Ref } from 'vue';
import type { School } from '@/types/school.interface';

const schoolInfo: Ref<School> = ref({ name: '', logoImage: '' });

onBeforeMount(() => {
    services.getSchoolInfo().then((res) => (schoolInfo.value = res));
});
</script>

<template>
    <div class="admin-school">
        <div>학교정보 관리</div>
        <div class="admin-school-content">
            <section class="admin-school-info">
                <h3 class="admin-school-title">학교 정보</h3>

                <div>
                    <img
                        class="admin-school-logo"
                        :src="schoolInfo.logoImage"
                        alt="logo" />
                </div>

                <div>
                    <VInput :id="schoolInfo.name" :value="schoolInfo.name" />
                </div>
            </section>
            <section class="admin-school-accounts">
                <h3 class="admin-school-title">학교 계정</h3>
            </section>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.admin-school {
    width: 100%;
    height: 100%;
}

.admin-school-content {
    display: flex;
    justify-content: space-around;
    height: 100%;
}

.admin-school-info,
.admin-school-accounts {
    width: 100%;
    height: 100%;
}

.admin-school-info {
    border-right: 0.1rem solid $admin-tertiary;
}

.admin-school-title {
    text-align: center;
    font-size: 1.5rem;
}

.admin-school-logo {
    width: 30%;
}
</style>
