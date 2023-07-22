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
            path: '/inbody',
            name: 'kiosk-inbody',
            component: () => import('@/views/kiosk/KioInbodyView.vue'),
        },
        {
            path: '/gym',
            name: 'kiosk-gym',
            component: () => import('@/views/kiosk/KioGymView.vue'),
        },
        {
            path: '/admin',
            name: 'admin',
            component: () => import('@/views/admin/AdmIndexView.vue'),
        },
        {
            path: '/admin/main',
            name: 'admin-main',
            component: () => import('@/views/admin/AdmMainView.vue'),
        },
    ],
});

export default router;
