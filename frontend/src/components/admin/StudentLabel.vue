<script setup lang="ts"></script>

<template>
    <tr class="student-label">
        <th class="student-label__content index"></th>
        <th class="student-label__content grade">학년</th>
        <th class="student-label__content room">반</th>
        <th class="student-label__content number">번호</th>
        <th class="student-label__content name">이름</th>
        <th class="student-label__content sex">성별</th>
        <th class="student-label__content birth">생년월일</th>
        <th class="student-label__content password">비밀번호</th>
    </tr>
</template>

<style lang="scss" scoped>
.student-label {
    display: grid;
    grid-template-areas: 'a b c d e e e f g g h h';
}

.student-label__content {
    border: 0.1rem solid $admin-secondary;
    padding: 0.2rem;

    &.index {
        grid-area: a;
    }

    &.grade {
        grid-area: b;
    }

    &.room {
        grid-area: c;
    }

    &.number {
        grid-area: d;
    }

    &.name {
        grid-area: e;
    }

    &.sex {
        grid-area: f;
    }

    &.birth {
        grid-area: g;
    }

    &.password {
        grid-area: h;
    }
}
</style>
