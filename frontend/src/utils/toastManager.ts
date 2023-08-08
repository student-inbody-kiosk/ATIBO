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

        toastRoot.style.position = 'fixed';
        toastRoot.style.top = '0';
        toastRoot.style.left = '0';
        toastRoot.style.right = '0';

        toastRoot.style.display = 'flex';
        toastRoot.style.flexDirection = 'column';
        toastRoot.style.gap = '5px';

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
    let message = defaultMessage;

    // 1. If there's detail error reason
    if (err?.response?.data?.detail) {
        message = err.response.data.detail;
        return toastManager.toast(message, 'error', 2500, 'center', 'lg');
    }
    try {
        // 2. if the error is validation, get the first validation error reason
        if (
            (err?.response?.status === 400) &
            Object.keys(err?.response?.data).length
        ) {
            const errObj = err.response.data;
            const firstKey = Object.keys(errObj)[0];
            const firstValue = errObj[firstKey];
            if (Array.isArray(firstValue)) {
                message = firstValue[0];
            }
        }
    } catch {
        // 3. return the default error message
        return toastManager.toast(message, 'error', 2500, 'center', 'lg');
    }

    return toastManager.toast(message, 'error', 2500, 'center', 'lg');
}

// toast success message at the center
export function toastCenterSuccessMessage(message) {
    toastManager.toast(message, 'success', 2500, 'center', 'lg');
}

// toast error detail message at the top
export function toastTopErrorMessage(defaultMessage, err = null) {
    let message = defaultMessage;

    // 1. If there's detail error reason
    if (err?.response?.data?.detail) {
        message = err.response.data.detail;
        return toastManager.toast(message, 'error', 2500);
    }
    try {
        // 2. if the error is validation, get the first validation error reason
        if (
            (err?.response?.status === 400) &
            Object.keys(err?.response?.data).length
        ) {
            const errObj = err.response.data;
            const firstKey = Object.keys(errObj)[0];
            const firstValue = errObj[firstKey];
            if (Array.isArray(firstValue)) {
                message = firstValue[0];
            }
        }
    } catch {
        // 3. return the default error message
        return toastManager.toast(message, 'error', 2500);
    }

    toastManager.toast(message, 'error', 2500);
}

// toast success message  at the top
export function toastTopSuccessMessage(message) {
    toastManager.toast(message, 'success', 2500);
}
