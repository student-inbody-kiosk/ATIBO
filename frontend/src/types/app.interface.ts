export interface Header {
    title: string;
    routeName: string;
    routeParams?: object;
    routeQuery?: object;
}

export interface HeaderUpdate {
    title?: string;
    routeName?: string;
    routeParams?: object;
    routeQuery?: object;
}
