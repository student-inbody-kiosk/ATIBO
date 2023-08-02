import * as auth from './auth';
import * as attendances from './attendances';
import * as accounts from './accounts';
import * as students from './students';
import * as schools from './schools';

const services = {
    ...auth,
    ...attendances,
    ...accounts,
    ...students,
    ...schools,
};

export default services;
