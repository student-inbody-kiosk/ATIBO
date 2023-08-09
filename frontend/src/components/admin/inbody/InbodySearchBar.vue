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
                @input="(value) => $emit('start-date', value)" />
            <VInput
                id="endDate"
                label="끝"
                type="date"
                :value="endDate"
                size="md"
                @input="(value) => $emit('end-date', value)" />
        </div>
        <div class="inbody-searchbar-student">
            <VInput
                id="grade"
                label="학년"
                :value="grade"
                size="md"
                @input="(value) => $emit('grade', value)" />
            <VInput
                id="room"
                label="반"
                :value="room"
                size="md"
                @input="(value) => $emit('room', value)" />
            <VInput
                id="number"
                label="번호"
                :value="number"
                size="md"
                @input="(value) => $emit('number', value)" />
            <VInput
                id="name"
                label="이름"
                :value="name"
                size="md"
                @input="(value) => $emit('name', value)" />
            <VButton
                text="조회"
                color="admin-primary"
                @click="$emit('search')" />
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
