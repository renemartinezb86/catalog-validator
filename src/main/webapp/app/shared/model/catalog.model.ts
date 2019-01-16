import { Moment } from 'moment';
import { ITaxModel } from 'app/shared/model//tax-model.model';
import { IBaseItem } from 'app/shared/model//base-item.model';
import { IHierarchy } from 'app/shared/model//hierarchy.model';
import { IInfoModel } from 'app/shared/model//info-model.model';

export interface ICatalog {
    id?: string;
    name?: string;
    fileName?: string;
    createdDate?: Moment;
    taxModels?: ITaxModel[];
    baseItems?: IBaseItem[];
    hierarchies?: IHierarchy[];
    infoModels?: IInfoModel[];
}

export class Catalog implements ICatalog {
    constructor(
        public id?: string,
        public name?: string,
        public fileName?: string,
        public createdDate?: Moment,
        public taxModels?: ITaxModel[],
        public baseItems?: IBaseItem[],
        public hierarchies?: IHierarchy[],
        public infoModels?: IInfoModel[]
    ) {}
}
