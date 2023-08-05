import { createRouter, createWebHistory } from 'vue-router';

import KioView from '@/views/kiosk/KioView.vue';
import KioIndexView from '@/views/kiosk/KioIndexView.vue';
import KioAttendView from '@/views/kiosk/KioAttendView.vue';
import KioInbodyView from '@/views/kiosk/KioInbodyView.vue';
import KioInbodyListView from '@/views/kiosk/KioInbodyListView.vue';
import KioInbodyDetailView from '@/views/kiosk/KioInbodyDetailView.vue';
import KioInbodyPwView from '@/views/kiosk/KioInbodyPwView.vue';
import KioGymView from '@/views/kiosk/KioGymView.vue';
import KioGymDetailView from '@/views/kiosk/KioGymDetailView.vue';

import AdmView from '@/views/admin/AdmView.vue';
import AdmIndexView from '@/views/admin/AdmIndexView.vue';
import AdmMainView from '@/views/admin/AdmMainView.vue';
import AdmStudentView from '@/views/admin/AdmStudentView.vue';
import AdmStudentCreateView from '@/views/admin/AdmStudentCreateView.vue';
import AdmStudentDeleteView from '@/views/admin/AdmStudentDeleteView.vue';
import AdmStudentUpdateView from '@/views/admin/AdmStudentUpdateView.vue';
import AdmAttendView from '@/views/admin/AdmAttendView.vue';
import AdmInbodyView from '@/views/admin/AdmInbodyView.vue';
import AdmGymView from '@/views/admin/AdmGymView.vue';
import AdmGymDetailView from '@/views/admin/AdmGymDetailView.vue';
import AdmGymUpdateView from '@/views/admin/AdmGymUpdateView.vue';
import AdmSchoolView from '@/views/admin/AdmSchoolView.vue';

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
                    path: 'inbody/:grade/:room/:number',
                    name: 'kiosk-inbody-list',
                    component: KioInbodyListView,
                },
                {
                    path: 'inbody/:grade/:room/:number/detail/:inbodyId',
                    name: 'kiosk-inbody-detail',
                    component: KioInbodyDetailView,
                },
                {
                    path: 'inbody/:grade/:room/:number/pw',
                    name: 'kiosk-inbody-pw',
                    component: KioInbodyPwView,
                },
                {
                    path: 'gym',
                    name: 'kiosk-gym',
                    component: KioGymView,
                },
                {
                    path: 'gym/:gymId',
                    name: 'kiosk-gym-detail',
                    component: KioGymDetailView,
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
                    path: 'student/create',
                    name: 'admin-student-create',
                    component: AdmStudentCreateView,
                },
                {
                    path: 'student/update',
                    name: 'admin-student-update',
                    component: AdmStudentUpdateView,
                },
                {
                    path: 'student/delete',
                    name: 'admin-student-delete',
                    component: AdmStudentDeleteView,
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
                {
                    path: 'gym',
                    name: 'admin-gym',
                    component: AdmGymView,
                },
                {
                    path: 'gym/:gymId',
                    name: 'admin-gym-detail',
                    component: AdmGymDetailView,
                },
                {
                    path: 'gym/:gymId/update',
                    name: 'admin-gym-update',
                    component: AdmGymUpdateView,
                },
                {
                    path: 'school',
                    name: 'admin-school',
                    component: AdmSchoolView,
                },
            ],
        },
    ],
});

export default router;
