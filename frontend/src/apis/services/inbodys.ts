import apiRequest from '@/apis/axiosInterceptors';
import type { InbodyDetail } from '@/types/inbody.interface';
import { toastCenterErrorMessage } from '@/utils/toastManager';

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
        .then((res): InbodyDetail[] => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('인바디 정보를 불러오지 못했습니다', err);
            throw err;
        });
}

export async function getInbody(inbodyId) {
    return await apiRequest
        .get(`/students/inbody/${inbodyId}/`)
        .then((res): InbodyDetail => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('인바디 정보를 불러오지 못했습니다', err);
            throw err;
        });
}
