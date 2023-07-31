<script setup lang="ts">
withDefaults(
    defineProps<{
        label?: string;
        type?: 'text' | 'number' | 'password' | 'month' | 'date' | 'checkbox';
        refer: string;
        value?: number | string;
        min?: number;
        max?: number;
    }>(),
    { type: 'text' }
);
</script>

<template>
    <div>
        <label :for="refer">{{ label }}</label>
        <input
            :type="type"
            :id="refer"
            :value="value"
            :min="min"
            :max="max"
            @input="(e: any) => $emit('update-input', e.target.value)"
            @keyup.enter="$emit('submit')" />
    </div>
</template>

<style lang="scss"></style>
