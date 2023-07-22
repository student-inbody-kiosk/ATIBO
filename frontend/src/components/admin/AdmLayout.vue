<script setup lang="ts"></script>

<template>
    <div class="admin-container">
        <header>
            <slot name="admin-header"></slot>
        </header>

        <main class="admin-container__main">
            <slot name="admin-main"></slot>
        </main>

        <footer class="admin-container__footer">
            ⓒ 2022. ATIBO all rights reserved.
        </footer>
    </div>
</template>

<style lang="scss" scoped>
.admin-container {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: min-content 1fr min-content;
    justify-content: center;
    min-height: 100vh;
    background-color: $white;
}

.admin-container__main {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: $admin-secondary;
}
.admin-container__footer {
    text-align: center;
    color: $gray;
}
</style>
