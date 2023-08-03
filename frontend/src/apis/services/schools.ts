import apiRequest from '@/apis/axiosInterceptors';
import toastManager, { toastCenterErrorMessage } from '@/utils/toastManager';
import type { School } from '@/types/school.interface';

export function getSchoolInfo() {
    return apiRequest
        .get('/school/')
        .then((res): School => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('학교 정보 조회에 실패했습니다', err);
            throw err;
        });
}

export function updateSchoolInfo(name, logoImage) {
    return apiRequest
        .put(
            '/school/',
            { name, logoImage },
            { headers: { 'Content-Type': 'multipart/form-data' } }
        )
        .then((res): School => {
            return res.data;
        });
