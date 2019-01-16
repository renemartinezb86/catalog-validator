import { IEnvironment } from 'app/shared/model//environment.model';
import { ICatalog } from 'app/shared/model//catalog.model';

export interface IValidation {
    id?: string;
    importValidationFile?: string;
    ecmValidateFile?: string;
    timestampMark?: string;
    environment?: IEnvironment;
    catalog?: ICatalog;
}

export class Validation implements IValidation {
    constructor(
        public id?: string,
        public importValidationFile?: string,
        public ecmValidateFile?: string,
        public timestampMark?: string,
        public environment?: IEnvironment,
        public catalog?: ICatalog
    ) {}
}
