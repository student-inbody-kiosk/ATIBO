import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import '@/styles/main.scss';

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faCircleArrowLeft,
    faUserLock,
    faHouse,
    faCircleMinus,
    faXmark,
    faShare,
    faLock,
} from '@fortawesome/free-solid-svg-icons';

library.add(
    faCircleArrowLeft,
    faUserLock,
    faHouse,
    faCircleMinus,
    faXmark,
    faShare,
    faLock
);

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.use(router);
app.component('font-awesome-icon', FontAwesomeIcon);
app.mount('#app');
