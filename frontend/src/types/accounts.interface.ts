export interface Account {
    id?: string;
    username: string;
    name: string;
    email: string;
    role?: string;
}

export interface SchoolAccount extends Account {
    comment: string;
    isActive: boolean;
}

export interface AccountList {
    inactiveUsers: SchoolAccount[];
    activeUsers: SchoolAccount[];
}

export interface AccountSignup extends Account {
    password: string;
    comment: string;
}
