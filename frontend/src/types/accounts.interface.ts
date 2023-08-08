export interface Account {
    id?: string;
    username: string;
    name: string;
    email: string;
    role?: string;
    comment: string;
}

export interface SchoolAccount extends Account {
    isActive: boolean;
}

export interface AccountList {
    inactiveUsers: SchoolAccount[];
    activeUsers: SchoolAccount[];
}

export interface AccountSignup extends Account {
    password: string;
}

export interface AccountPwReset {
    username: string;
    email: string;
}

export interface AccountPwChange {
    oldPassword: string;
    newPassword: string;
    confirmPassword: string;
}
