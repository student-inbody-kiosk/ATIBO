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
