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
    grid-template-columns: 1fr;
    grid-template-rows: minmax(min-content, auto) minmax(0, auto);
    height: 100vh;
    background-color: $kiosk-secondary;
}

/* 스크롤바 전체 기본 꾸미기 */
.kiosk-container ::-webkit-scrollbar {
    width: 13px; /* 스크롤바의 너비 */
}

/* 스크롤바 막대 꾸미기 */
.kiosk-container ::-webkit-scrollbar-thumb {
    background: $kiosk-primary; /* 스크롤바의 색상 */
    border-radius: 10px;
}

/* 스크롤바 트랙 꾸미기 */
.kiosk-container ::-webkit-scrollbar-track {
    background: transparent;
}
</style>
