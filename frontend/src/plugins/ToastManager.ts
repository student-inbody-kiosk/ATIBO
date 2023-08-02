import VToast from '@/components/common/VToast.vue';
import { createVNode, h, render } from 'vue';

export default {
    install: (app, options) => {
        // Select the toastRoot element
        let toastRoot = document.querySelector('#toast');

        // If not, Create the toastRoot element
        if (!toastRoot) {
            toastRoot = document.createElement('div');
            toastRoot.id = 'toast';
            document.body.appendChild(toastRoot);
        }

        // CSS styling for toastRoot
        toastRoot.style.display = 'flex';
        toastRoot.style.flexDirection = 'column-reverse';
        toastRoot.style.alignItems = 'center';
        toastRoot.style.position = 'fixed';
        toastRoot.style.left = '0';
        toastRoot.style.right = '0';

        // Create toastMessage
        const toast = (
            message,
            type = 'info',
            duration = 2500,
            position = 'top',
            size = 'sm'
        ) => {
            // Make Vue Node
            const toastVNode = h(VToast, {
                message,
                type,
                position,
                size,
                onClick: deleteToast,
            });

            // Render the VNode
            const toast = document.createElement('div');
            render(toastVNode, toast);

            // Append to the toastRoot
            toastRoot.append(toast);
            setTimeout(() => {
                toast.remove();
            }, duration);
        };

        // Delete the clicked toastMessage
        const deleteToast = (event) => {
            event.target.parentElement.remove();
        };

        // provide the toast method
        app.provide('toast', toast);
    },
};
