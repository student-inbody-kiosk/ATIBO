<script setup lang="ts"></script>

<template>
    <thead>
        <tr class="student-attend-label">
            <th class="student-attend-label__content"></th>
            <th class="student-attend-label__content">학년</th>
            <th class="student-attend-label__content">반</th>
            <th class="student-attend-label__content">번호</th>
            <th class="student-attend-label__content">이름</th>
        </tr>
    </thead>
</template>

<style lang="scss" scoped>
thead {
    @include z-index(label);
    position: sticky;
    top: 0;
}
.student-attend-label {
    background-color: $admin-tertiary;
}

.student-attend-label__content {
    min-width: 3.5rem;
    padding: 0.2rem;
    border: 0.1rem solid $admin-secondary;
}
</style>
