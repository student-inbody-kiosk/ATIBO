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

<style lang="scss">
body {
    background-color: $kiosk-secondary;
}
</style>
