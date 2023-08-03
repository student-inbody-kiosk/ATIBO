import { ref, inject } from 'vue';
import { defineStore } from 'pinia';
import services from '@/apis/services';
import type { Student } from '@/types/students.interface';

export const useStudentStore = defineStore('stduent', {
    state: () => {
        // reset student
        function $reset() {
            student = null;
        }
        // state
        return { student: null as Student | null, $reset };
    },
    actions: {
        // update student synchronously
        updateStudent(payload: Student) {
            this.student = payload;
        },
        // update student asynchronously
        async getStudent(grade: number, room: number, number: number) {
            try {
                await services.getTheStudent(grade, room, number);
            } catch (err) {
                return err;
            }
        },
    },
});
