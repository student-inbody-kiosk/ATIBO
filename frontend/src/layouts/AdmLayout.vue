<script setup lang="ts"></script>

<template>
    <div class="admin-container">
        <header class="admin-container__header">
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
body {
    background-color: $white;
}
.admin-container {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto minmax(0, 1fr) auto;
    justify-content: center;
    height: 100vh;
    margin: 0 2rem;
    background-color: $white;
}

.admin-container ::-webkit-scrollbar {
    width: 12px;
    height: 12px;
}

.admin-container ::-webkit-scrollbar-thumb {
    background: $admin-primary;
    border-radius: 10px;
}

.admin-container ::-webkit-scrollbar-track {
    background: transparent;
}

.admin-container__header {
    padding: 0.7rem 0 0.3rem;
}

.admin-container__main {
    display: flex;
    justify-content: center;
    padding: 2rem;
    background-color: $admin-secondary;
}
.admin-container__footer {
    text-align: center;
    color: $gray;
    padding: 1rem;
}
</style>
