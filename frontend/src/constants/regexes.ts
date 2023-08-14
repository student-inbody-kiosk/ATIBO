const regexes = {
    studentNum: {
        reg: /^[1-9]\d{4,5}$/,
        condition: '학번 형식에 맞춰 입력해주세요',
    },
    startDate: {
        reg: /^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$/,
        condition: '시작일 날짜 형식을 확인해주세요',
    },
    endDate: {
        reg: /^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$/,
        condition: '종료일 날짜 형식을 확인해주세요',
    },
};

const studentRegexes = {
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
        condition: '비밀번호는 4자리 숫자로 입력해주세요',
    },
};

const inbodyRegexes = {
    testDate: {
        reg: /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1])$/,
        condition: '날짜는 YYYY-MM-DD 형식으로 입력해주세요',
    },
    weight: {
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
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
        reg: /^(?:[1-9][0-9]*|0)(?:\.\d+)?$/,
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

const accountRegexes = {
    username: {
        reg: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,20}$/,
        condition: '영어와 숫자를 조합하여 5~20자로 입력해주세요',
    },
    name: {
        reg: /^[가-힣]{2,5}$/,
        condition: '한글 2~5자로 입력해주세요',
    },
    email: {
        reg: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
        condition: '이메일 형식으로 입력해주세요',
    },
    password: {
        reg: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/,
        condition: '영어, 숫자, 특수문자를 조합하여 8~24자로 입력해주세요',
    },
};

export { regexes, studentRegexes, accountRegexes, inbodyRegexes };
