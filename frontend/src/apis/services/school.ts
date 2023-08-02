import apiRequest from '@/apis/axiosInterceptors';

interface schoolInfo {
    name: string;
    logoImage: string;
}

export function getSchoolInfo() {
    return apiRequest.get('/school/').then((res): schoolInfo => {
        return res.data;
    });
}
