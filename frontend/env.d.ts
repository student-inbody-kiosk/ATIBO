/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_BASE_URL: string;
}

declare module '*.svg' {
    const filePath: string;
    export default filePath;
}
