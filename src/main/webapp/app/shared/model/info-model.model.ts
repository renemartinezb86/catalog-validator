import { ICatalog } from 'app/shared/model//catalog.model';
import { IInfoModelAttr } from 'app/shared/model//info-model-attr.model';

export interface IInfoModel {
    id?: string;
    code?: string;
    name?: string;
    status?: string;
    desc?: string;
    isDynam?: string;
    dbTableName?: string;
    expirationalInterval?: string;
    type?: string;
    subtype?: string;
    catalog?: ICatalog;
    attributes?: IInfoModelAttr[];
}

export class InfoModel implements IInfoModel {
    constructor(
        public id?: string,
        public code?: string,
        public name?: string,
        public status?: string,
        public desc?: string,
        public isDynam?: string,
        public dbTableName?: string,
        public expirationalInterval?: string,
        public type?: string,
        public subtype?: string,
        public catalog?: ICatalog,
        public attributes?: IInfoModelAttr[]
    ) {}
}
