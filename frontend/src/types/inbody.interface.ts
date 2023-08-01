export interface Inbody {
    name: string;
    grade: number;
    room: number;
    number: number;
    inbodySet: InbodyDetail[];
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
