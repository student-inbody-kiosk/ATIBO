<script setup lang="ts"></script>

<template>
    <div class="kiosk-container">
        <header>
            <slot name="kiosk-header"></slot>
        </header>
        <main class="kiosk-container__main">
            <slot name="kiosk-main"></slot>
        </main>
    </div>
</template>

<style lang="scss" scoped>
.kiosk-container {
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: auto minmax(0, 1fr);
    overflow: hidden;
    height: 100vh;
    background-color: $kiosk-secondary;
}

.kiosk-container ::-webkit-scrollbar {
    width: 12px;
}

.kiosk-container ::-webkit-scrollbar-thumb {
    background: $kiosk-primary;
    border-radius: 10px;
}

.kiosk-container ::-webkit-scrollbar-track {
    background: transparent;
}

.kiosk-container__main {
    display: grid;
    grid-template-columns: minmax(0, 1fr);
    grid-template-rows: minmax(0, 1fr);
    width: 100%;
    height: 100%;
}
</style>
