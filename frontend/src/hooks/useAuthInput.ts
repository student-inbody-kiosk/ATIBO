import { ref } from 'vue';
import { accountRegexes } from '@/constants/regexes';

const useAuthInput = (type: 'username' | 'name' | 'email' | 'password') => {
    // data ref & regex test result
    const value = ref('');
    const result = ref(false);

    // regex info
    const regex = accountRegexes[type].reg;
    const condition = accountRegexes[type].condition;

    const handleInput = (data: string) => {
        value.value = data;
        result.value = regex.test(data);
    };

    return { value, result, condition, handleInput };
};

const usePasswordCheck = (password: any) => {
    // data ref & regex test result
    const value = ref('');
    const result = ref(false);

    const condition = '비밀번호와 일치된 값으르 입력해주세요';

    const handleInput = (data: string) => {
        value.value = data;
        result.value = password.value === data ? true : false;
    };

    return { value, result, condition, handleInput };
};

export { useAuthInput, usePasswordCheck };
