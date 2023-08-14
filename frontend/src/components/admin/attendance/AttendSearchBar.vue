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

const emit = defineEmits<{
    (e: 'date', value: string): void;
    (e: 'grade', value: string): void;
    (e: 'room', value: string): void;
    (e: 'number', value: string): void;
    (e: 'name', value: string): void;
    (e: 'enter'): void;
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
                size="md"
                @input="(value) => $emit('date', value)"
                @enter="$emit('enter')" />
            <span>출결 기록에 마우스를 올리면 상세 기록을 볼 수 있습니다</span>
        </div>
        <div class="attend-searchbar__student">
            <VInput
                id="grade"
                label="학년"
                :value="grade"
                size="md"
                @input="(value) => $emit('grade', value)"
                @enter="$emit('enter')" />

            <VInput
                id="room"
                label="반"
                :value="room"
                size="md"
                @input="(value) => $emit('room', value)"
                @enter="$emit('enter')" />

            <VInput
                id="number"
                label="번호"
                :value="number"
                size="md"
                @input="(value) => $emit('number', value)"
                @enter="$emit('enter')" />

            <VInput
                id="name"
                label="이름"
                :value="name"
                size="md"
                @input="(value) => $emit('name', value)"
                @enter="$emit('enter')" />

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
