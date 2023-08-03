import VToast from '@/components/common/VToast.vue';
import { createVNode, h, render } from 'vue';

class ToastManager {
    // Set the Toast Root
    constructor() {
        let toastRoot = document.querySelector('#toast');

        // If not, Create the toastRoot element
        if (!toastRoot) {
            toastRoot = document.createElement('div');
            toastRoot.id = 'toast';
            document.body.appendChild(toastRoot);
        }

        this.root = toastRoot;
    }

    toast = (
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
            onClick: this.deleteToast,
        });

        // Render the VNode
        const toast = document.createElement('div');
        render(toastVNode, toast);

        // Append to the toastRoot
        this.root.append(toast);
        setTimeout(() => {
            toast.remove();
        }, duration);
    };

    deleteToast = (event) => {
        event.target.parentElement.remove();
    };
}

const toastManager = new ToastManager();

export default toastManager;

// toast error detail message at the center
export function toastCenterErrorMessage(defaultMessage, err = null) {
    const message = err?.response?.data?.detail
        ? err.response.data.detail
        : defaultMessage;
    toastManager.toast(message, 'error', 2500, 'center', 'lg');
}

// toast success message at the center
export function toastCenterSuccessMessage(message) {
    toastManager.toast(message, 'success', 2500, 'center', 'lg');
}

// toast error detail message at the top
export function toastTopErrorMessage(defaultMessage, err = null) {
    const message = err?.response?.data?.detail
        ? err.response.data.detail
        : defaultMessage;
    toastManager.toast(message, 'error', 2500);
}

// toast success message  at the top
export function toastTopSuccessMessage(message) {
    toastManager.toast(message, 'success', 2500);
}
