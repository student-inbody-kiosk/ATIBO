<script setup lang="ts"></script>

<template>
    <div class="admin-container">
        <header>
            <slot name="admin-header"></slot>
        </header>

        <main>
            <slot name="admin-main"></slot>
        </main>

        <footer class="admin-footer">ⓒ 2022. ATIBO all rights reserved.</footer>
    </div>
</template>

<style lang="scss" scoped>
.admin-container {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: min-content 1fr min-content;
    min-height: 100vh;
    background-color: $white;
}
.admin-footer {
    text-align: center;
    color: $gray;
}
</style>
