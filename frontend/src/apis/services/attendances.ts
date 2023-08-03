import apiRequest from '@/apis/axiosInterceptors';
import {
    toastCenterErrorMessage,
    toastCenterSuccessMessage,
} from '@/utils/toastManager';
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
            toastCenterSuccessMessage('출석되었습니다');
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('출석에 실패했습니다', err);
            throw err;
        });
}
