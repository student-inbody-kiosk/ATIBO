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
    <div class="admin-attend">
        <div class="admin-attend__searchbar-date">
            <VInput
                id="date"
                label="월별 검색"
                type="month"
                refer="month"
                :value="date"
                @input="(value) => $emit('date', value)" />
        </div>
        <div class="admin-attend__searchbar-student">
            <VInput
                id="grade"
                label="학년"
                refer="grade"
                :value="grade"
                @input="(value) => $emit('grade', value)" />
            <VInput
                id="room"
                label="반"
                refer="room"
                :value="room"
                @input="(value) => $emit('room', value)" />
            <VInput
                id="number"
                label="번호"
                refer="number"
                :value="number"
                @input="(value) => $emit('number', value)" />
            <VInput
                id="name"
                label="이름"
                refer="name"
                :value="name"
                @input="(value) => $emit('name', value)" />
            <VButton
                text="조회"
                color="admin-primary"
                @click="$emit('search')" />
        </div>
    </div>
</template>

<style lang="scss"></style>
