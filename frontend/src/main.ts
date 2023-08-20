import App from './App.vue';
import { createApp } from 'vue';
import '@/styles/main.scss';

import router from './router';

import { createMetaManager } from 'vue-meta';

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
    faLock,
    faMobileScreen,
    faShare,
    faUser,
    faUserLock,
    faUserPlus,
    faXmark,
} from '@fortawesome/free-solid-svg-icons';

// create vue app
const app = createApp(App);

// router
app.use(router);

// vue meta
app.use(createMetaManager());

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
    faLock,
    faMobileScreen,
    faShare,
    faUser,
    faUserLock,
    faUserPlus,
    faXmark
);
app.component('font-awesome-icon', FontAwesomeIcon);

// mount the vue app to index.html
app.mount('#app');
