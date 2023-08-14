<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';

defineProps<{
    startDate: string;
    endDate: string;
    grade: string;
    room: string;
    number: string;
    name: string;
}>();

const emit = defineEmits<{
    (e: 'start-date', value: string): void;
    (e: 'end-date', value: string): void;
    (e: 'grade', value: string): void;
    (e: 'room', value: string): void;
    (e: 'number', value: string): void;
    (e: 'name', value: string): void;
    (e: 'enter'): void;
}>();
</script>

<template>
    <div class="inbody-searchbar">
        <div class="inbody-searchbar-date">
            <VInput
                id="startDate"
                label="시작"
                type="date"
                :value="startDate"
                size="md"
                @input="(value) => $emit('start-date', value)"
                @enter="$emit('enter')" />
            <VInput
                id="endDate"
                label="끝"
                type="date"
                :value="endDate"
                size="md"
                @input="(value) => $emit('end-date', value)"
                @enter="$emit('enter')" />
        </div>
        <div class="inbody-searchbar-student">
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
                @click="$emit('enter')" />
        </div>
    </div>
</template>

<style lang="scss" scoped>
.inbody-searchbar-date,
.inbody-searchbar-student {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.25rem 0;
}
</style>
