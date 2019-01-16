import { ICatalog } from 'app/shared/model//catalog.model';

export interface IBaseItem {
    id?: string;
    basename?: string;
    baseType?: string;
    itemName?: string;
    itemType?: string;
    itemSubtype?: string;
    catalog?: ICatalog;
}

export class BaseItem implements IBaseItem {
    constructor(
        public id?: string,
        public basename?: string,
        public baseType?: string,
        public itemName?: string,
        public itemType?: string,
        public itemSubtype?: string,
        public catalog?: ICatalog
    ) {}
}
