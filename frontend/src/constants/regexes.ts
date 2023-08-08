const regexes = {
    studentNum: /^[1-9]\d{4,5}$/,
    studentPw: /^\d{4}$/,
    date: /^\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$/,
    username: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,20}$/,
    email: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
    password:
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/,
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

export default regexes;

export { accountRegexes };
