<script setup lang="ts"></script>

<template lang="">
    <div class="kiosk-input-guide">
        <slot />
    </div>
</template>

<style lang="scss">
.kiosk-input-guide {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    padding: 2rem;
    background-color: #ffffffc7;
    border-radius: 1em;
    font-size: 3vh;
    font-weight: 600;
    text-align: center;
    line-height: 6vh;
}
</style>
