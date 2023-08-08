const regexes = {
    studentNum: /^[1-9]\d{4,5}$/,
    studentPw: /^\d{4}$/,
    date: /^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$/,
    username: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,20}$/,
    email: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
    password:
        /^(?=.[A-Za-z])(?=.\d)(?=.[()[\]{}|\\`~!@#$%^&\-+=;:,<>./?])[A-Za-z\d()[\]{}|\\~!@#$%^&*\-+=;:,<>./?]{8,24}$/,
};

export default regexes;

export const studentRegexes = {
    name: {
        reg: /^[가-힣]{2,5}$/,
        condition: '학생 이름은 한글 2-5자로 입력해주세요',
    },
    grade: {
        reg: /^[1-9]$/,
        condition: '학년은 1~9 사이의 숫자로 입력해주세요',
    },
    room: {
        reg: /^(?:[1-9]|[1-9][0-9])$/,
        condition: '반은 1~99 사이의 숫자로 입력해주세요',
    },
    number: {
        reg: /^(?:[1-9][0-9]?|100)$/,
        condition: '번호는 1~100 사이의 숫자로 입력해주세요',
    },
    sex: { reg: /^[0129]$/, condition: '성별을 정확히 입력해주세요' },
    birthDate: {
        reg: /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1])$/,
        condition: '날짜는 YYYY-MM-DD 형식으로 입력해주세요',
    },
    password: {
        reg: /^\d{4}$/,
        condition: '비밀번호는 숫자 네자리로 입력해주세요',
    },
};

export const inbodyRegexes = {
    testDate: {
        reg: /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1])$/,
        condition: '날짜는 YYYY-MM-DD 형식으로 입력해주세요',
    },
    weight: {
        reg: /^[1-9][0-9]*$/,
        condition: '체중을 정확히 입력해주세요',
    },
    percentBodyFat: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: '체지방률을 정확히 입력해주세요',
    },
    skeletalMuscleMass: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: '골격근량을 정확히 입력해주세요',
    },
    height: {
        reg: /^[1-9][0-9]*$/,
        condition: '키를 정확히 입력해주세요',
    },
    age: {
        reg: /^[1-9][0-9]*$/,
        condition: '나이는 1~127 사이의 숫자로 입력해주세요',
    },
    totalBodyWater: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: '체수분을 정확히 입력해주세요',
    },
    protein: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: '단백질을 정확히 입력해주세요',
    },
    minerals: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: '무기질을 정확히 입력해주세요',
    },
    bodyFatMass: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: '체지방을 정확히 입력해주세요',
    },
    bodyMassIndex: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
        condition: 'BMI를 정확히 입력해주세요',
    },
    score: {
        reg: /^(?:[1-9]|[1-9][0-9]|100)$/,
        condition: '인바디 점수는 1~100 사이의 숫자로 입력해주세요',
    },
};
