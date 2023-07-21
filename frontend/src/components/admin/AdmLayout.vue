<script setup lang="ts"></script>

<template>
    <div class="admin-container">
        <header>
            <slot name="admin-header"></slot>
        </header>

        <main>
            <slot name="admin-main"></slot>
        </main>

        <footer>
            <slot name="admin-footer"></slot>
        </footer>
    </div>
</template>

<style lang="scss"></style>
