<script setup lang="ts"></script>

<template>
    <div class="kiosk-container">
        <header>
            <slot name="kiosk-header"></slot>
        </header>

        <main>
            <slot name="kiosk-main"></slot>
        </main>
    </div>
</template>

<style lang="scss" scoped>
.kiosk-container {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: min-content 1fr min-content;
    min-height: 100vh;
    background-color: $kiosk-secondary;
}
</style>
