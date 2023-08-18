<script setup lang="ts"></script>

<template>
    <thead>
        <tr class="student-data-label">
            <th class="student-data-label__content" id="empty"></th>
            <th class="student-data-label__content" id="grade">학년</th>
            <th class="student-data-label__content" id="room">반</th>
            <th class="student-data-label__content" id="number">번호</th>
            <th class="student-data-label__content" id="name">이름</th>
            <th class="student-data-label__content" id="sex">성별</th>
            <th class="student-data-label__content" id="birthDate">생년월일</th>
            <th class="student-data-label__content" id="password">비밀번호</th>
        </tr>
    </thead>
</template>

<style lang="scss" scoped>
thead {
    @include z-index(label);
    position: sticky;
    top: 0;
}
.student-data-label {
    background-color: $admin-tertiary;
}

.student-data-label__content {
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
}
</style>
