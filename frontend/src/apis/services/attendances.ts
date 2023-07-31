import apiRequest from '@/apis/axiosInterceptors';
// import router from '@/router/index';

export async function getAttendances(
    month: string,
    grade?: number | null,
    room?: number | null,
    number?: number | null,
    name?: string | null
) {
    return await apiRequest.get(`/students/attendance/${month}/`, {
        params: {
            grade,
            room,
            number,
            name,
        },
    });
}
