<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';

defineProps<{
    date: string;
    grade: string;
    room: string;
    number: string;
    name: string;
}>();
</script>

<template>
    <div>
        <div class="attend-searchbar__date">
            <VInput
                id="date"
                label="월 입력"
                type="month"
                :value="date"
                @input="(value) => $emit('date', value)" />
            <span>출결 기록에 마우스를 올리면 상세 기록을 볼 수 있습니다</span>
        </div>
        <div class="attend-searchbar__student">
            <VInput
                id="grade"
                label="학년"
                :value="grade"
                @input="(value) => $emit('grade', value)" />
            <VInput
                id="room"
                label="반"
                :value="room"
                @input="(value) => $emit('room', value)" />
            <VInput
                id="number"
                label="번호"
                :value="number"
                @input="(value) => $emit('number', value)" />
            <VInput
                id="name"
                label="이름"
                :value="name"
                @input="(value) => $emit('name', value)" />
            <VButton
                text="조회"
                color="admin-primary"
                @click="$emit('search')" />
        </div>
    </div>
</template>

<style lang="scss" scoped>
.attend-searchbar__date {
    display: flex;
    align-items: center;
    gap: 1rem;

    span {
        color: $gray-dark;
        font-weight: 600;
        font-size: 0.9rem;
    }
}
.attend-searchbar__student {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.5rem 0;
}
</style>
