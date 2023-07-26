import apiRequest from '@/apis/axiosInterceptors';
import type { Student } from '@/types/students.interface';

export async function getStudents(
    grade?: number,
    room?: number,
    number?: number,
    name?: string
) {
    return await apiRequest
        .get('/students/', {
            params: {
                grade,
                room,
                number,
                name,
            },
        })
        .then((res) => console.log(res.data));
}

export async function createStudents(students: Student[]) {
    return await apiRequest
        .post('/students/', students)
        .then(() => console.log('SUCCESS'));
}
