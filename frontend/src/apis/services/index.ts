import * as auth from './auth';
import * as attendances from './attendances';
import * as accounts from './accounts';
import * as students from './students';
import * as schools from './schools';
import * as inbodys from './inbodys';

const services = {
    ...auth,
    ...attendances,
    ...accounts,
    ...students,
    ...schools,
    ...inbodys,
};

export default services;
