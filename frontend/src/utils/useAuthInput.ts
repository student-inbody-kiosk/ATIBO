import { ref } from 'vue';
import regexes from '@/constants/regexes';

const regCondition = {
    username: '영어와 숫자 조합 5~20자. 최소 1개의 문자 포함',
    email: '이메일 형식',
    password: '문자, 숫자, 특수문자 조합 8~24자. 각각 1개의 문자 포함',
};

const useAuthInput = (type: 'username' | 'email' | 'password') => {
    const value = ref('');
    const result = ref(false);
    const condition = regCondition[type];

    const handleInput = (data: string) => {
        value.value = data;
        result.value = regexes[type]?.test(data);
    };

    return { result, value, handleInput, condition };
};

const usePasswordCheck = (password: any) => {
    const value = ref('');
    const result = ref(false);

    const handleInput = (data: string) => {
        value.value = data;
        result.value = password.value === data ? true : false;
    };

    return { result, value, handleInput };
};

export { useAuthInput, usePasswordCheck };
