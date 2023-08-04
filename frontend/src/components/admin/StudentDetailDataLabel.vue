<script setup lang="ts"></script>

<template>
    <thead>
        <tr class="student-data-label">
            <th class="student-data-label__content"></th>
            <th class="student-data-label__content">학년</th>
            <th class="student-data-label__content">반</th>
            <th class="student-data-label__content">번호</th>
            <th class="student-data-label__content">이름</th>
            <th class="student-data-label__content">성별</th>
            <th class="student-data-label__content">생년월일</th>
            <th class="student-data-label__content">비밀번호</th>
        </tr>
    </thead>
</template>

<style lang="scss" scoped>
.student-data-label {
    background-color: $admin-tertiary;
}

.student-data-label__content {
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
}
</style>
