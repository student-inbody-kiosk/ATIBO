import App from './App.vue';
import { createApp } from 'vue';
import '@/styles/main.scss';

import router from './router';

import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faCircleArrowLeft,
    faUserLock,
    faUserPlus,
    faHouse,
    faCircleMinus,
    faXmark,
    faShare,
    faLock,
    faFileCircleMinus,
} from '@fortawesome/free-solid-svg-icons';

import CKEditor from '@ckeditor/ckeditor5-vue';

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
    faCircleArrowLeft,
    faUserLock,
    faUserPlus,
    faHouse,
    faCircleMinus,
    faXmark,
    faShare,
    faLock,
    faFileCircleMinus
);
app.component('font-awesome-icon', FontAwesomeIcon);

// CKEditor
app.use(CKEditor);

// mount the vue app to index.html
app.mount('#app');
