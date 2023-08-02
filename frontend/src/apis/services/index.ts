import * as auth from './auth';
import * as accounts from './accounts';
import * as students from './students';
import * as school from './school';

const services = {
    ...auth,
    ...accounts,
    ...students,
    ...school,
};

export default services;
