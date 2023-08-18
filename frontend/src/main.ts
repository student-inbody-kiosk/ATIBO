import App from './App.vue';
import { createApp } from 'vue';
import '@/styles/main.scss';

import router from './router';

import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faCircleMinus,
    faCircleArrowLeft,
    faEnvelope,
    faFileCircleMinus,
    faHouse,
    faUser,
    faUserLock,
    faUserPlus,
    faLock,
    faShare,
    faXmark,
} from '@fortawesome/free-solid-svg-icons';

// create vue app
const app = createApp(App);

// router
app.use(router);

// pinia
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);

// font-awesmoe
library.add(
    faCircleMinus,
    faCircleArrowLeft,
    faEnvelope,
    faFileCircleMinus,
    faHouse,
    faUser,
    faUserLock,
    faUserPlus,
    faLock,
    faShare,
    faXmark
);
app.component('font-awesome-icon', FontAwesomeIcon);

// mount the vue app to index.html
app.mount('#app');
