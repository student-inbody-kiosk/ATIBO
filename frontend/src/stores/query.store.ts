import { defineStore } from 'pinia';
import { ref } from 'vue';

interface RouteQuery {
    startDate?: string | null;
    endDate?: string;
    month?: string;
    grade?: number;
    room?: number;
    number?: number;
    name?: string;
}

export const useQueryStore = defineStore('routeQuery', {
    state: () => {
        const routeQuery = ref<RouteQuery>({});

        function $reset() {
            routeQuery.value = {};
        }

        return { routeQuery, $reset };
    },
    actions: {
        updateQuery(payload: RouteQuery) {
            this.routeQuery = payload;
        },
    },
});
