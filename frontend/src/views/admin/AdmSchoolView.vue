<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import AdmAccountList from '@/components/admin/AdmAccountList.vue';
import { ref, onBeforeMount } from 'vue';

import services from '@/apis/services';

import type { SchoolAccount } from '@/types/accounts.interface';
import type { Ref } from 'vue';

import { useMeta } from 'vue-meta';

useMeta({
    title: 'ATIBO 아티보 학교 정보 관리',
    description: 'ATIBO 아티보 학교 정보 관리 페이지',
});

const schoolName = ref('');
const schoolLogo = ref('');

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

const fileInput = ref();
const handleLogoInput = function updateLogoFile(foo, target) {
    if (!target.files.length) return;

    const logoFile = target.files[0];
    fileInput.value = logoFile;
    let reader = new FileReader();
    reader.onload = function (e: any) {
        schoolLogo.value = e.target.result;
    };
    reader.readAsDataURL(logoFile);
};

const handleUpdateClick = function updateSchoolInfo() {
    services.updateSchoolInfo(schoolName.value, fileInput.value);
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

        <div class="admin-school-content">
            <section class="admin-school-info">
                <div class="admin-school-info__logo">
                    <img :src="schoolLogo" alt="logo" />
                    <span>{{ schoolName }}</span>
                </div>

                <div class="admin-school-info-container">
                    <div class="admin-school-info__update">
                        <VInput
                            :id="schoolName"
                            type="file"
                            size="md"
                            label="로고 선택"
                            @input="handleLogoInput" />
                        <VInput
                            :id="schoolName"
                            :value="schoolName"
                            size="md"
                            @input="(value) => (schoolName = value)"
                            label="학교 이름" />
                    </div>
                    <VButton
                        text="수정"
                        color="admin-primary"
                        @click="handleUpdateClick" />
                </div>
            </section>
            <section class="admin-school-accounts">
                <AdmAccountList
                    title="승인 대기"
                    :accountList="inactiveUsers"
                    @accept="handleAccpetClick" />
                <AdmAccountList
                    title="승인 완료"
                    :accountList="activeUsers"
                    @delete="handleDeleteClick" />
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
    grid-template-rows: auto minmax(0, 1fr);
    gap: 1rem;
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

.admin-school-content {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: 1fr;
}

.admin-school-info {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
}

.admin-school-info__logo {
    width: 90%;
    height: 50%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    background-color: $white;
    border-radius: 0.3rem;
    padding: 1.5rem;
    gap: 1rem;

    font-size: 1.5rem;
    font-weight: 600;

    img {
        width: 45%;
        height: auto;
        max-height: 90%;
    }
}
.admin-school-info-container {
    display: flex;
    align-items: center;

    button {
        align-self: flex-end;
    }
}

.admin-school-info__update {
    display: grid;
    gap: 1.5rem;

    div.v-input {
        display: flex;
        align-items: flex-start;
    }
}
</style>
