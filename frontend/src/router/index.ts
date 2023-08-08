import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth.store';

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
import AdmInbodyStudentView from '@/views/admin/AdmInbodyStudentView.vue';
import AdmInbodyDetailView from '@/views/admin/AdmInbodyDetailView.vue';
import AdmGymView from '@/views/admin/AdmGymView.vue';
import AdmGymDetailView from '@/views/admin/AdmGymDetailView.vue';
import AdmGymUpdateView from '@/views/admin/AdmGymUpdateView.vue';
import AdmSchoolView from '@/views/admin/AdmSchoolView.vue';
import {
    toastCenterErrorMessage,
    toastTopErrorMessage,
} from '@/utils/toastManager';

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
                    path: 'inbody/:grade(\\d+)/:room(\\d+)/:number(\\d+)',
                    name: 'kiosk-inbody-list',
                    component: KioInbodyListView,
                },
                {
                    path: 'inbody/:grade(\\d+)/:room(\\d+)/:number(\\d+)/detail/:inbodyId(\\d+)',
                    name: 'kiosk-inbody-detail',
                    component: KioInbodyDetailView,
                },
                {
                    path: 'inbody/:grade(\\d+)/:room(\\d+)/:number(\\d+)/pw',
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
                {
                    path: ':catchAll(.*)*',
                    redirect: { name: 'kiosk-index' },
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
                    path: 'inbody/student/:grade(\\d+)/:room(\\d+)/:number(\\d+)/:name(\\d+)',
                    name: 'admin-inbody-student',
                    component: AdmInbodyStudentView,
                },
                {
                    path: 'inbody/:grade(\\d+)/:room(\\d+)/:number(\\d+)/:name(\\d+)/detail/:inbodyId(\\d+)',
                    name: 'admin-inbody-detail',
                    component: AdmInbodyDetailView,
                },
                {
                    path: 'gym',
                    name: 'admin-gym',
                    component: AdmGymView,
                },
                {
                    path: 'gym/:gymId(\\d+)',
                    name: 'admin-gym-detail',
                    component: AdmGymDetailView,
                },
                {
                    path: 'gym/:gymId(\\d+)/update',
                    name: 'admin-gym-update',
                    component: AdmGymUpdateView,
                },
                {
                    path: 'school',
                    name: 'admin-school',
                    component: AdmSchoolView,
                },
                {
                    path: ':catchAll(.*)*',
                    redirect: { name: 'admin-index' },
                },
            ],
        },
    ],
});

// refreshToken is needed
const ADMIN_PRIVATE_ROUTES = [
    'admin-main',
    'admin-student',
    'admin-student-create',
    'admin-student-update',
    'admin-student-delete',
    'admin-attend',
    'admin-inbody',
    'admin-inbody-student',
    'admin-inbody-detail',
    'admin-gym',
    'admin-gym-detail',
    'admin-gym-update',
    'admin-school',
];

// accessToken is needed
const KIOSK_PRIVATE_ROUTES = [
    'kiosk-inbody-list',
    'kiosk-inbody-detail',
    'kiosk-inbody-pw',
];

// global navigation guard
router.beforeEach(async (to, from) => {
    const name = to.name;
    const { accessToken, refreshToken } = useAuthStore();

    if (ADMIN_PRIVATE_ROUTES.includes(name) && !refreshToken) {
        toastTopErrorMessage('다시 로그인 해주세요');
        return { name: 'admin-index' };
    } else if (KIOSK_PRIVATE_ROUTES.includes(name) && !accessToken) {
        toastCenterErrorMessage('다시 로그인 해주세요');
        return { name: 'kiosk-inbody' };
    }
});

export default router;
