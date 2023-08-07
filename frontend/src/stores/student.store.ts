import { ref, inject } from 'vue';
import { defineStore } from 'pinia';
import services from '@/apis/services';
import type { Student } from '@/types/students.interface';

export const useStudentStore = defineStore('student', {
    state: () => {
        // reset student
        function $reset() {
            student = null;
        }
        // state
        return { student: null as Student | null, $reset };
    },
    // getters: {
    //     studentSex(state) {
    //         const sexList = {
    //             0: '불확실',
    //             1: '남자',
    //             2: '여자',
    //             3: '그 외',
    //         };
    //         return sexList[state?.sex];
    //     },
    // },
    actions: {
        // update student synchronously
        updateStudent(payload: Student) {
            this.student = payload;
        },
        // update student asynchronously
        async getStudent(grade: number, room: number, number: number) {
            try {
                this.student = await services.getTheStudent(
                    grade,
                    room,
                    number
                );
            } catch (err) {
                return err;
            }
        },
    },
});
