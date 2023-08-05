<script setup lang="ts"></script>

<template lang="">
    <div class="kiosk-inbody-table-label">
        <p>검사일자</p>
        <p>몸무게</p>
        <p>체지방률</p>
        <p>골격근량</p>
    </div>
</template>

<style lang="scss">
.kiosk-inbody-table-label {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    justify-items: center;
    padding: 1.2rem 2rem;
    background-color: transparentize($kiosk-primary, 0.5);
    font-size: 1.3rem;
    font-weight: 600;
}
</style>
