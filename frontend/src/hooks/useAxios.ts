import { AxiosError } from 'axios';
import { ref, onMounted, onUnmounted } from 'vue';

export function useAxios<T>(defaultValue, cb: () => Promise) {
    const isLoading = ref(false);
    const isError = ref(false);
    const response = ref<T>(defaultValue);
    const error = ref<AxiosError | null>(null);

    const fetchData = function () {
        isLoading.value = true;
        cb()
            .then((res) => {
                response.value = res;
                isLoading.value = false;
            })
            .catch((er) => {
                error.value = err;
                isLoading.value = false;
                isError.value = true;
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
