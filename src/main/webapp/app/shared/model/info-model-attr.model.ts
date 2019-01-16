import { IInfoModel } from 'app/shared/model//info-model.model';

export interface IInfoModelAttr {
    id?: string;
    attrCode?: string;
    charType?: string;
    name?: string;
    isNull?: string;
    isSearch?: string;
    descending?: string;
    type?: string;
    assoctype?: string;
    seq?: string;
    infoModel?: IInfoModel;
}

export class InfoModelAttr implements IInfoModelAttr {
    constructor(
        public id?: string,
        public attrCode?: string,
        public charType?: string,
        public name?: string,
        public isNull?: string,
        public isSearch?: string,
        public descending?: string,
        public type?: string,
        public assoctype?: string,
        public seq?: string,
        public infoModel?: IInfoModel
    ) {}
}
