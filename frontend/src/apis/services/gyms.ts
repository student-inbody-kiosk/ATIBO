import apiRequest from '@/apis/axiosInterceptors';
import type {
    GymSimple,
    Gym,
    GymImage,
    GymUpdate,
} from '@/types/gyms.interface';
import {
    toastCenterErrorMessage,
    toastTopErrorMessage,
    toastTopSuccessMessage,
} from '@/utils/toastManager';
import { useRoute } from 'vue-router';

export function getGyms() {
    const route = useRoute();

    return apiRequest
        .get('/gym/')
        .then((res): GymSimple[] => {
            return res.data;
        })
        .catch((err) => {
            if (route.name.includes('kiosk')) {
                toastCenterErrorMessage('운동기구 조회에 실패했습니다', err);
            } else {
                toastTopErrorMessage('운동기구 조회에 실패했습니다', err);
            }
            throw err;
        });
}

export function getGym(gymId: number) {
    const route = useRoute();

    return apiRequest
        .get(`/gym/${gymId}/`)
        .then((res): Gym => {
            return res.data;
        })
        .catch((err) => {
            if (route.name.includes('kiosk')) {
                toastCenterErrorMessage('운동기구 조회에 실패했습니다', err);
            } else {
                toastTopErrorMessage('운동기구 조회에 실패했습니다', err);
            }
            throw err;
        });
}

export function createGym(gym: GymUpdate) {
    return apiRequest
        .post('/gym/', gym)
        .then((res): Gym => {
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('운동기구 생성에 실패했습니다', err);
            throw err;
        });
}

export function updateGym(gymId: nmber, data: GymUpdate) {
    return apiRequest
        .put(`/gym/${gymId}/`, data)
        .then((res): Gym => {
            toastTopSuccessMessage('운동기구 설명이 수정되었습니다');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('운동기구 설명을 수정하지 못했습니다', err);
            throw err;
        });
}

export function deleteGym(gymId: nmber) {
    return apiRequest
        .delete(`/gym/${gymId}/`)
        .then((res): Gym => {
            toastTopSuccessMessage('운동기구가 삭제되었습니다');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('운동기구를 삭제하지 못했습니다', err);
            throw err;
        });
}

export function getGymImages(gymId: number) {
    const route = useRoute();

    return apiRequest
        .get(`/gym/${gymId}/image/`)
        .then((res): GymImage[] => {
            return res.data;
        })
        .catch((err) => {
            if (route.name.includes('kiosk')) {
                toastCenterErrorMessage(
                    '운동기구 사진 조회에 실패했습니다',
                    err
                );
            } else {
                toastTopErrorMessage('운동기구 사진 조회에 실패했습니다', err);
            }
            throw err;
        });
}

export function updateGymImages(gymId: number, data: GymImage[]) {
    return apiRequest
        .put(`/gym/${gymId}/image/`, data, {
            headers: { 'Content-Type': 'multipart/form-data' },
        })
        .then((res): GymImage[] => {
            toastTopSuccessMessage('운동기구 사진이 수정되었습니다');
            return res.data;
        })
        .catch((err) => {
            toastTopErrorMessage('운동기구 사진을 수정하지 못했습니다', err);
            throw err;
        });
}
