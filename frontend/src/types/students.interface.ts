export interface StudentSimple {
    id: string;
    grade: number;
    room: number;
    number: number;
    name: string;
}

export interface Student extends StudentSimple {
    sex: number;
    birthDate: string;
}

export interface StudentDetail extends Student {
    password: string;
}
