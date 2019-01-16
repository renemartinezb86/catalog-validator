import { ICatalog } from 'app/shared/model//catalog.model';
import { IIND } from 'app/shared/model//ind.model';
import { ITaxObject } from 'app/shared/model//tax-object.model';
import { ITaxModel } from 'app/shared/model//tax-model.model';

export interface ITaxModel {
    id?: string;
    catalog?: ICatalog;
    oIND?: IIND;
    relatedTaxObject?: ITaxObject;
    taxModel?: ITaxModel;
    relatedTaxModels?: ITaxModel[];
}

export class TaxModel implements ITaxModel {
    constructor(
        public id?: string,
        public catalog?: ICatalog,
        public oIND?: IIND,
        public relatedTaxObject?: ITaxObject,
        public taxModel?: ITaxModel,
        public relatedTaxModels?: ITaxModel[]
    ) {}
}
