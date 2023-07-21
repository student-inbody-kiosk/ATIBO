import { createRouter, createWebHistory } from 'vue-router';

import KioIndexView from '@/views/kiosk/KioIndexView.vue';
import KioAttendView from '@/views/kiosk/KioAttendView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'kiosk',
            component: KioIndexView,
        },
        {
            path: '/attend',
            name: 'kiosk-attend',
            component: KioAttendView,
        },
        {
            path: '/admin',
            name: 'admin',
            component: () => import('@/views/admin/AdmIndexView.vue'),
        },
    ],
});

export default router;
