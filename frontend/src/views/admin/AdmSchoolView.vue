<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import AdmAccountList from '@/components/admin/AdmAccountList.vue';
import { ref, onBeforeMount } from 'vue';

import services from '@/apis/services';

import type { SchoolAccount } from '@/types/accounts.interface';
import type { Ref } from 'vue';
const schoolName = ref('');
const schoolLogo = ref('');
const fileInput = ref();

const activeUsers: Ref<SchoolAccount[]> = ref([]);
const inactiveUsers: Ref<SchoolAccount[]> = ref([]);

onBeforeMount(() => {
    services.getSchoolInfo().then((res) => {
        schoolName.value = res.name;
        schoolLogo.value = res.logoImage;
    });
    services.getSchoolAccounts().then((res) => {
        activeUsers.value = res.activeUsers;
        inactiveUsers.value = res.inactiveUsers;
    });
});

const handleLogoInput = function updateLogoFile(e: any) {
    if (!e.target.files.length) return;

    const logoFile = e.target.files[0];
    let reader = new FileReader();
    reader.onload = function (e: any) {
        schoolLogo.value = e.target.result;
    };
    reader.readAsDataURL(logoFile);
};

const handleUpdateClick = function updateSchoolInfo() {
    services.updateSchoolInfo(schoolName.value, fileInput.value?.files[0]);
};

const handleAccpetClick = function acceptAccount(userId: number) {
    services.acceptAccount(userId).then(() => {
        services.getSchoolAccounts().then((res) => {
            activeUsers.value = res.activeUsers;
            inactiveUsers.value = res.inactiveUsers;
        });
    });
};

const handleDeleteClick = function deleteAccount(userId: number) {
    if (!confirm('정말 삭제하시겠습니까? 되돌릴 수 없습니다.')) return;
    services.deleteAccount(userId).then(() => {
        services.getSchoolAccounts().then((res) => {
            activeUsers.value = res.activeUsers;
            inactiveUsers.value = res.inactiveUsers;
        });
    });
};
</script>

<template>
    <div class="admin-school">
        <div class="admin-school-header">
            <VButton
                text="뒤로"
                color="gray"
                @click="$router.push({ name: 'admin-main' })" />
            <div>학교정보 관리</div>
        </div>

        <div class="admin-school-title">
            <div>학교 정보</div>
            <div>학교 계정</div>
        </div>

        <div class="admin-school-content">
            <section class="admin-school-accounts">
                <AdmAccountList
                    title="승인 대기 목록"
                    :accountList="inactiveUsers"
                    @accept="handleAccpetClick" />
                <AdmAccountList
                    title="승인 완료 목록"
                    :accountList="activeUsers"
                    @delete="handleDeleteClick" />
            </section>
            <section class="admin-school-info">
                <img class="school-logo" :src="schoolLogo" alt="logo" />
                <div class="admin-school-title">{{ schoolName }}</div>

                <div class="admin-school-update">
                    <VInput :id="schoolName" type="file" label="로고 선택" />
                    <input
                        type="file"
                        ref="fileInput"
                        @input="handleLogoInput" />
                    <VInput
                        :id="schoolName"
                        :value="schoolName"
                        @input="(value) => (schoolName = value)"
                        label="학교 이름" />
                    <VButton
                        text="수정"
                        color="admin-primary"
                        @click="handleUpdateClick" />
                </div>
            </section>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.admin-school {
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto auto minmax(0, 1fr);
}

.admin-school-header {
    display: grid;
    grid-template-rows: 1fr;
    grid-template-columns: auto minmax(0, 1fr);
    padding-bottom: 1rem;

    div {
        font-size: 1.4rem;
        font-weight: 600;
        text-align: center;
    }
}

.admin-school-title {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    padding: 1rem 0;
    font-size: 1.3rem;
}

.admin-school-content {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
}

.school-logo {
    width: 10rem;
}

.admin-school-info,
.admin-school-accounts {
    width: 100%;
    height: 100%;
}

.admin-school-info {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.admin-school-update {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.admin-school-accounts {
    height: 100%;
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: minmax(0, 1fr) minmax(0, 1fr);
    gap: 0.5rem;
}
</style>
