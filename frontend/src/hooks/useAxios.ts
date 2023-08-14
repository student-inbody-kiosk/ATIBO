import { AxiosError } from 'axios';
import { ref, onMounted, onUnmounted } from 'vue';

// T: response type
// V: request data type
export function useAxios<T>(defaultValue, cb: (...args) => Promise) {
    const isLoading = ref(false);
    const isError = ref(false);
    const response = ref<T>(defaultValue);
    const error = ref<AxiosError | null>(null);

    const fetchData = async function (...args) {
        isLoading.value = true;
        return await cb(...args)
            .then((res) => {
                response.value = res;
                isLoading.value = false;
                return res;
            })
            .catch((err) => {
                error.value = err;
                isLoading.value = false;
                isError.value = true;
                throw err;
            });
    };

    return {
        fetchData,
        isLoading,
        isError,
        response,
        error,
    };
}
