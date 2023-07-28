import * as auth from './auth';
import * as accounts from './accounts';
import * as students from './students';

const services = {
    ...auth,
    ...accounts,
    ...students,
};

export default services;
