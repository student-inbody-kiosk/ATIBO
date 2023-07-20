import { createRouter, createWebHistory } from 'vue-router';

import KioIndexView from '@/views/kiosk/KioIndexView.vue';
import KioAttendView from '@/views/kiosk/KioAttendView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'kio-index',
            component: KioIndexView,
        },
        {
            path: '/attend',
            name: 'kio-attend',
            component: KioAttendView,
        },
        // {
        //     path: '/about',
        //     name: 'about',
        //     // route level code-splitting
        //     // this generates a separate chunk (About.[hash].js) for this route
        //     // which is lazy-loaded when the route is visited.
        //     component: () => import('../views/AboutView.vue'),
        // },
    ],
});

export default router;
