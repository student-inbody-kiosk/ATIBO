import { createRouter, createWebHistory } from 'vue-router';

import KioView from '@/views/kiosk/KioView.vue';
import KioIndexView from '@/views/kiosk/KioIndexView.vue';
import KioAttendView from '@/views/kiosk/KioAttendView.vue';
import KioInbodyView from '@/views/kiosk/KioInbodyView.vue';
import KioGymView from '@/views/kiosk/KioGymView.vue';

import AdmView from '@/views/admin/AdmView.vue';
import AdmIndexView from '@/views/admin/AdmIndexView.vue';
import AdmMainView from '@/views/admin/AdmMainView.vue';
import AdmStudentView from '@/views/admin/AdmStudentView.vue';
import AdmAttendView from '@/views/admin/AdmAttendView.vue';
import AdmInbodyView from '@/views/admin/AdmInbodyView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'kiosk',
            component: KioView,
            children: [
                {
                    path: '',
                    name: 'kiosk-index',
                    component: KioIndexView,
                },
                {
                    path: 'attend',
                    name: 'kiosk-attend',
                    component: KioAttendView,
                },
                {
                    path: 'inbody',
                    name: 'kiosk-inbody',
                    component: KioInbodyView,
                },
                {
                    path: 'gym',
                    name: 'kiosk-gym',
                    component: KioGymView,
                },
            ],
        },

        {
            path: '/admin',
            name: 'admin',
            component: AdmView,
            children: [
                {
                    path: '',
                    name: 'admin-index',
                    component: AdmIndexView,
                },
                {
                    path: 'main',
                    name: 'admin-main',
                    component: AdmMainView,
                },
                {
                    path: 'student',
                    name: 'admin-student',
                    component: AdmStudentView,
                },
                {
                    path: 'attend',
                    name: 'admin-attend',
                    component: AdmAttendView,
                },
                {
                    path: 'inbody',
                    name: 'admin-inbody',
                    component: AdmInbodyView,
                },
            ],
        },
    ],
});

export default router;
