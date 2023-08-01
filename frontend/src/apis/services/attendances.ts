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

export async function createAttendacne(
    grade: number,
    room: number,
    number: number
) {
    return await apiRequest
        .post(`/students/attendance/${grade}/${room}/${number}/`)
        .then((res) => {
            return res.data;
        })
        .catch((err) => {
            return err.response.data;
        });
}
