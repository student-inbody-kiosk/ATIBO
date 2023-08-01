import apiRequest from '@/apis/axiosInterceptors';

export async function getInbodys(
    startDate: string,
    endDate: string,
    grade?: number | null,
    room?: number | null,
    number?: number | null,
    name?: string | null
) {
    return await apiRequest.get(`/students/inbody/${startDate}/${endDate}/`, {
        params: {
            grade,
            room,
            number,
            name,
        },
    });
}

export async function getTheStudentInbodys(
    grade: number,
    room: number,
    number: number,
    startDate?: string,
    endDate?: string
) {
    return await apiRequest
        .get(`/students/inbody/${grade}/${room}/${number}/`, {
            params: {
                startDate,
                endDate,
            },
        })
        .then((res) => {
            return res.data;
        });
}
