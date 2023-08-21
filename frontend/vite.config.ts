import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import svgLoader from 'vite-svg-loader';
import { VitePWA } from 'vite-plugin-pwa';

export default defineConfig({
    plugins: [vue(), svgLoader(), VitePWA({ registerType: 'autoUpdate' })],
    resolve: {
        alias: [{ find: '@', replacement: '/src' }],
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: '@import "@/styles/utils/index";',
            },
        },
    },
});
