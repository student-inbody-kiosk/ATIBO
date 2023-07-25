import apiRequest from '@/apis/axiosInterceptors';

export async function getStudents(
    grade?: number,
    room?: number,
    number?: number,
    name?: string
) {
    return await apiRequest
        .get('/students', {
            params: {
                grade,
                room,
                number,
                name,
            },
        })
        .then((res) => console.log(res.data));
}
