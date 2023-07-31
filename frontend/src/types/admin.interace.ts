export interface Student {
    id: string;
    grade: number;
    room: number;
    number: number;
    name: string;
    sex: number;
    birthDate: string;
    password: string;
}

export interface Account {
    id: string;
    username: string;
    name: string;
    email: string;
    role: string;
}

// TODO: AttendanceDetail로 수정
export interface AttendanceInfo {
    id: number;
    dateAttended: string;
}

export interface Attendance {
    name: string;
    grade: number;
    room: number;
    attendanceSet: AttendanceInfo[];
}

// TODO: Student Type 따로 분리
export interface InbodyDetail {
    id: number;
    testDate: string;
    weight: number;
    percentBodyFat: number;
    skeletalMuscleMass: number;
    height: number;
    age: number;
    totalBodyWater: number;
    protein: number;
    minerals: number;
    bodyFatMass: number;
    bodyMassIndex: number;
    score: number;
}

export interface Inbody {
    name: string;
    grade: number;
    room: number;
    number: number;
    inbodySet: InbodyDetail[];
}
