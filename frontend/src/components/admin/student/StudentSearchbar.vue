<script setup lang="ts">
import VInput from '@/components/common/VInput.vue';
import VButton from '@/components/common/VButton.vue';
import { useQueryStore } from '@/stores/query.store';
import { computed } from 'vue';

const query = computed(() => {
    const { routeQuery } = useQueryStore();
    const { grade, room, number, name } = routeQuery;

    return { grade, room, number, name };
});

withDefaults(
    defineProps<{
        grade: string;
        room: string;
        number: string;
        name: string;
        isStudentData: boolean;
    }>(),
    {
        isStudentData: false,
    }
);
</script>

<template>
    <div class="student-search-bar">
        <div class="student-search-bar__input">
            <VInput
                id="grade"
                label="학년"
                :value="grade"
                @input="(value: string) => $emit('grade', value)"
                @enter="$emit('enter')" />
            <VInput
                id="room"
                label="반"
                :value="room"
                @input="(value: string) => $emit('room', value)"
                @enter="$emit('enter')" />
            <VInput
                id="number"
                label="번호"
                :value="number"
                @input="(value: string) => $emit('number', value)"
                @enter="$emit('enter')" />
            <VInput
                id="name"
                label="이름"
                :value="name"
                @input="(value: string) => $emit('name', value)"
                @enter="$emit('enter')" />
            <VButton
                text="조회"
                color="admin-primary"
                @click="$emit('search')" />
        </div>
        <div class="student-search-bar__buttons">
            <VButton
                text="추가"
                color="green"
                @click="$router.push({ name: 'admin-student-create' })" />
            <VButton
                v-if="isStudentData"
                text="수정"
                color="admin-primary"
                @click="
                    $router.push({ name: 'admin-student-update', query })
                " />
            <VButton
                v-if="isStudentData"
                text="삭제"
                color="red"
                @click="
                    $router.push({ name: 'admin-student-delete', query })
                " />
        </div>
    </div>
</template>

<style lang="scss" scoped>
.student-search-bar {
    display: flex;
    justify-content: space-between;
    gap: 1.5rem;
    padding-bottom: 0.5rem;
}
.student-search-bar__buttons {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}
.student-search-bar__input {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 1.5rem;
}
</style>
