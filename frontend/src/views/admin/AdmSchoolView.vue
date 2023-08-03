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

const dummy = {
    inactiveUsers: [
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: false,
        },
    ],
    activeUsers: [
        {
            id: '76f77a4f-aba0-42f0-9241-f0e13346ffa1',
            username: 'user2',
            name: '김길동',
            email: 'user@email.com',
            comment: '5학년 2반 담임 고길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
        {
            id: '76f77a4f-aba0-42f0-9241-f0e189a7ffa1',
            username: 'user1',
            name: '홍길동',
            email: 'user@email.com',
            comment: '2학년 3반 담임 홍길동입니다.',
            isActive: true,
        },
    ],
};
</script>

<template>
    <div class="admin-school">
        <div>뒤로 학교정보 관리 앞으로</div>

        <div class="admin-school-header">
            <div class="admin-school-title">학교 정보</div>
            <div class="admin-school-title">학교 계정</div>
        </div>

        <div class="admin-school-content">
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
                        size="sm"
                        @click="handleUpdateClick" />
                </div>
            </section>
            <section class="admin-school-accounts">
                <!-- TODO: 계정 승인 삭제 API 연결 -->
                <AdmAccountList
                    title="승인 대기"
                    :accountList="dummy.inactiveUsers" />
                <AdmAccountList
                    title="승인 완료"
                    :accountList="dummy.activeUsers" />
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
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    padding-bottom: 0.5rem;
}

.admin-school-title {
    font-size: 1.5rem;
}

.admin-school-content {
    display: flex;
    justify-content: space-between;
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
