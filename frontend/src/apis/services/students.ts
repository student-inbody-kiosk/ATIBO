import apiRequest from '@/apis/axiosInterceptors';
import { useAuthStore } from '@/stores/auth.store';
import toastManager, {
    toastCenterErrorMessage,
    toastCenterSuccessMessage,
} from '@/utils/toastManager';
import type { Student, StudentSimple } from '@/types/students.interface';
import { useStudentStore } from '@/stores/student.store';

export async function getStudents(
    grade?: number | null,
    room?: number | null,
    number?: number | null,
    name?: string | null
) {
    return await apiRequest.get('/students/', {
        params: {
            grade,
            room,
            number,
            name,
        },
    });
}

export async function createStudents(students: Student[]) {
    return await apiRequest.post('/students/', students);
}

export async function deleteStudents(ids: string[]) {
    return await apiRequest.patch('/students/', { ids: ids });
}

export async function updateStudents(students: Student[]) {
    return await apiRequest.put('/students/', students);
}

export async function checkStudent(
    grade: number,
    room: number,
    number: number
) {
    return await apiRequest
        .get(`/students/${grade}/${room}/${number}/check/`)
        .then((res): StudentSimple => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('학생 조회에 실패했습니다', err);
            throw err;
        });
}

export async function loginStudent(
    grade: number,
    room: number,
    number: number,
    password: string
) {
    return await apiRequest
        .post(`/students/${grade}/${room}/${number}/login/`, { password })
        .then((res) => {
            // save tokens
            const { updateAccessToken, updateRefreshToken } = useAuthStore();
            updateAccessToken(res.data.accessToken);
            updateRefreshToken(res.data.refreshToken);
            // get the student info from pinia
            const studentStore = useStudentStore();
            studentStore.getStudent(grade, room, number);
        })
        .catch((err) => {
            toastCenterErrorMessage('로그인에 실패했습니다', err);
            throw err;
        });
}

export async function getTheStudent(
    grade: number,
    room: number,
    number: number
) {
    return await apiRequest
        .get(`/students/${grade}/${room}/${number}/`)
        .then((res): Student => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('학생 정보를 불러오지 못했습니다', err);
            throw err;
        });
}

export async function updateStudentPw(
    grade: number,
    room: number,
    number: number,
    oldPassword: string,
    newPassword: string,
    confirmPassword: string
) {
    return await apiRequest
        .put(`/students/${grade}/${room}/${number}/password/change/`, {
            oldPassword,
            newPassword,
            confirmPassword,
        })
        .then((res) => {
            toastCenterSuccessMessage('비밀번호가 변경되었습니다');
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('비밀번호 변경에 실패했습니다', err);
            throw err;
        });
}
