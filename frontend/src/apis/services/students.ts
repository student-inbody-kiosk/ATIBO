import apiRequest from '@/apis/axiosInterceptors';
import type { Student } from '@/types/students.interface';

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
