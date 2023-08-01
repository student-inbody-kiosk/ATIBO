<script setup lang="ts"></script>

<template lang="">
    <div class="kiosk-input-guide">
        <p>
            학년, 반, 번호를 입력해주세요 <br />
            예시 1학년 1반 1번 -> 10101
        </p>
    </div>
</template>

<style lang="scss">
.kiosk-input-guide {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    width: 100%;
    padding: 2rem;
    background-color: #ffffffc7;
    border-radius: 5px;
    text-align: center;
    font-size: 1.5rem;
    font-weight: 600;
    line-height: 200%;
}
</style>
