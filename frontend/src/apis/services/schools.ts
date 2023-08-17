import { useRoute } from 'vue-router';
import apiRequest from '@/apis/axiosInterceptors';
import {
    toastCenterErrorMessage,
    toastTopErrorMessage,
    toastTopSuccessMessage,
} from '@/utils/toastManager';
import type { School } from '@/types/school.interface';

export function getSchoolInfo() {
    const route = useRoute();

    return apiRequest
        .get('/school/')
        .then((res): School => {
            return res.data;
        })
        .catch((err) => {
            if (route.name.includes('kiosk')) {
                toastCenterErrorMessage('학교 정보 조회에 실패했습니다', err);
            } else {
                toastTopErrorMessage('학교 정보 조회에 실패했습니다', err);
            }
            throw err;
        });
}

export function updateSchoolInfo(name: string, logoImage: File) {
    return apiRequest
        .put(
            '/school/',
            { name, logoImage },
            { headers: { 'Content-Type': 'multipart/form-data' } }
        )
        .then((res): School => {
            toastTopSuccessMessage('학교 정보 수정에 성공했습니다.');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('학교 정보 수정에 실패했습니다', err);
            throw err;
        });
}
