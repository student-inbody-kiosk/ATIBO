<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import { ref, onBeforeMount } from 'vue';

import services from '@/apis/services';

import type { School } from '@/types/school.interface';
import type { Ref } from 'vue';

const schoolName = ref('');
const schoolLogo = ref('');
const fileInput = ref();

onBeforeMount(() => {
    services.getSchoolInfo().then((res: School) => {
        schoolName.value = res.name;
        schoolLogo.value = res.logoImage;
    });
});

const handleLogoInput = function updateLogoFile(e: any) {
    console.log(e.target.files);
    if (!e.target.files.length) return;

    // TODO: JPEG 형식만 로드가 안됨
    const logoFile = e.target.files[0];
    let reader = new FileReader();
    reader.onload = function (e: any) {
        schoolLogo.value = e.target.result;
    };
    reader.readAsDataURL(logoFile);
};

const handleUpdateClick = function updateSchoolInfo() {
    const formData = new FormData();
    if (fileInput.value.files.length) {
        const logoFile = fileInput.value.files[0];
        formData.append('logo_image', logoFile);
    }

    services
        .updateSchoolInfo(schoolName.value, formData)
        .then(() => console.log('성공'));
};
</script>

<template>
    <div class="admin-school">
        <div>학교정보 관리</div>
        <div class="admin-school-content">
            <section class="admin-school-info">
                <h3 class="admin-school-title">학교 정보</h3>

                <div class="admin-school-detail">
                    <img :src="schoolLogo" alt="logo" />
                    <span>{{ schoolName }}</span>
                </div>

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
                        class="admin-school-update-button"
                        text="수정"
                        color="admin-primary"
                        size="sm"
                        @click="handleUpdateClick" />
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
    align-items: center;
    justify-content: space-around;
    height: 100%;
}

.admin-school-info,
.admin-school-accounts {
    width: 100%;
    height: 90%;
}

.admin-school-info {
    border-right: 0.1rem solid $admin-tertiary;
}

.admin-school-title {
    margin-bottom: 3rem;
    text-align: center;
    font-size: 1.5rem;
}

.admin-school-detail {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
    font-size: 2rem;

    img {
        width: 40%;
    }
}

.admin-school-update {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin: 2rem;
}

.admin-school-update-button {
    display: flex;
    align-self: flex-end;
}
</style>
