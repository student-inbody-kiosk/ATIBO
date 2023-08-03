import apiRequest from '@/apis/axiosInterceptors';
import type { GymDetail, GymSimple } from '@/types/gyms.interface';
import { toastCenterErrorMessage } from '@/utils/toastManager';

export function getGyms() {
    return apiRequest
        .get('/gym/')
        .then((res): GymSimple[] => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('운동기구 조회에 실패했습니다', err);
            throw err;
        });
}

export function getGym(gymId) {
    return apiRequest
        .get(`/gym/${gymId}`)
        .then((res): GymDetail => {
            return res.data;
        })
        .catch((err) => {
            toastCenterErrorMessage('운동기구 조회에 실패했습니다', err);
            throw err;
        });
}
