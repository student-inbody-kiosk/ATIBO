import apiRequest from '@/apis/axiosInterceptors';
import type { Student } from '@/types/students.interface';
import { useAuthStore } from '@/stores/auth.store';

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
        .then((res): SimpleStudent => {
            return res.data;
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
            const { updateAccessToken, updateRefreshToken } = useAuthStore();
            updateAccessToken(res.data.accessToken);
            updateRefreshToken(res.data.refreshToken);
        });
}

export async function getTheStudent(
    grade: number,
    room: number,
    number: number
) {
    return await apiRequest
        .get(`/students/${grade}/${room}/${number}/`)
        .then((res) => {
            return res.data;
        });
}
