export interface Attendance {
    [key: number]: {
        id: number;
        time: string;
    };
}

export interface StudentAttendance {
    name: string;
    grade: number;
    room: number;
    number: number;
    attendanceSet: Attendance[];
}
