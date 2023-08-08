import { inbodyRegexes, studentRegexes } from '@/constants/regexes';
import { toastTopErrorMessage } from './toastManager';
import type { Student } from '@/types/students.interface';
import type { InbodyDetail } from '@/types/inbody.interface';

// 정규식 검사를 만족하지 않으면 index 반환, 만족한다면 false 반환
const checkStudentInput = (student: Student, index) => {
    for (const key in student) {
        if (key === 'id') continue;
        if (!studentRegexes[key].reg.test(student[key])) {
            toastTopErrorMessage(studentRegexes[key].condition);
            return index;
        }
    }
    return false;
};

const checkInbodyInput = (inbody: InbodyDetail) => {
    for (const key in inbody) {
        if (key === 'id') continue;
        if (!inbodyRegexes[key].reg.test(inbody[key])) {
            toastTopErrorMessage(inbodyRegexes[key].condition);
            return index;
        }
    }
    return false;
};

export { checkInbodyInput, checkStudentInput };
