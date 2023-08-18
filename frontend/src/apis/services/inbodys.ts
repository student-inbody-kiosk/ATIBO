import { useRoute } from 'vue-router';
import apiRequest from '@/apis/axiosInterceptors';
import {
    toastCenterErrorMessage,
    toastTopErrorMessage,
    toastTopSuccessMessage,
} from '@/utils/toastManager';
import type { InbodyDetail } from '@/types/inbody.interface';

export async function getInbodys(
    startDate: string,
    endDate: string,
    grade?: number | null,
    room?: number | null,
    number?: number | null,
    name?: string | null
) {
    return await apiRequest
        .get(`/students/inbody/${startDate}/${endDate}/`, {
            params: { grade, room, number, name },
        })
        .then((res) => {
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('인바디 정보를 불러오지 못했습니다', err);
            throw err;
        });
}

export async function getTheStudentInbodys(
    grade: number,
    room: number,
    number: number,
    startDate?: string,
    endDate?: string
) {
    const route = useRoute();

    return await apiRequest
        .get(`/students/inbody/${grade}/${room}/${number}/`, {
            params: { startDate, endDate },
        })
        .then((res): InbodyDetail[] => {
            return res.data;
        })
        .catch((err) => {
            if (route.name.includes('kiosk')) {
                toastCenterErrorMessage(
                    '인바디 정보를 불러오지 못했습니다',
                    err
                );
            } else {
                toastTopErrorMessage('인바디 정보를 불러오지 못했습니다', err);
            }
            throw err;
        });
}

export async function getInbody(inbodyId: number) {
    const route = useRoute();

    return await apiRequest
        .get(`/students/inbody/${inbodyId}/`)
        .then((res): InbodyDetail => {
            return res.data;
        })
        .catch((err) => {
            if (route.name.includes('kiosk')) {
                toastCenterErrorMessage(
                    '인바디 정보를 불러오지 못했습니다',
                    err
                );
            } else {
                toastTopErrorMessage('인바디 정보를 불러오지 못했습니다', err);
            }
            throw err;
        });
}

export async function createInbody(
    grade: number,
    room: number,
    number: number,
    inbody: InbodyDetail
) {
    return await apiRequest
        .post(`students/inbody/${grade}/${room}/${number}/`, inbody)
        .then(() => {
            toastTopSuccessMessage('인바디가 추가되었습니다');
        })
        .catch((err) => {
            toastTopErrorMessage('인바디 추가에 실패했습니다', err);
            throw err;
        });
}

export async function updateInbody(inbodyId: number, inbody: InbodyDetail) {
    return await apiRequest
        .put(`students/inbody/${inbodyId}/`, inbody)
        .then(() => {
            toastTopSuccessMessage('인바디가 수정되었습니다');
        })
        .catch((err) => {
            toastTopErrorMessage('인바디 정보 수정에 실패했습니다', err);
            throw err;
        });
}

export async function deleteInbody(inbodyId: number) {
    return await apiRequest
        .delete(`students/inbody/${inbodyId}/`)
        .then(() => {
            toastTopSuccessMessage('인바디가 삭제되었습니다');
        })
        .catch((err) => {
            toastTopErrorMessage('인바디 삭제에 실패했습니다', err);
            throw err;
        });
}
