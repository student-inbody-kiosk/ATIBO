import apiRequest from '@/apis/axiosInterceptors';
import type { School } from '@/types/school.interface';

export function getSchoolInfo() {
    return apiRequest.get('/school/').then((res): School => {
        return res.data;
    });
}

export function updateSchoolInfo(name: string, logoImage: any) {
    return apiRequest.put(
        '/school/',
        { name, logoImage },
        { headers: { 'Content-Type': 'multipart/form-data' } }
    );
}
