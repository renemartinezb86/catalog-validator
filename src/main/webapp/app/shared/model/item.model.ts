import { ISimpleItem } from 'app/shared/model//simple-item.model';
import { IIND } from 'app/shared/model//ind.model';
import { ICharacteristic } from 'app/shared/model//characteristic.model';
import { IAttribute } from 'app/shared/model//attribute.model';

export interface IItem {
    id?: string;
    name?: string;
    index?: number;
    toDelete?: boolean;
    unique?: boolean;
    simpleItem?: ISimpleItem;
    ind?: IIND;
    characteristics?: ICharacteristic[];
    attributes?: IAttribute[];
}

export class Item implements IItem {
    constructor(
        public id?: string,
        public name?: string,
        public index?: number,
        public toDelete?: boolean,
        public unique?: boolean,
        public simpleItem?: ISimpleItem,
        public ind?: IIND,
        public characteristics?: ICharacteristic[],
        public attributes?: IAttribute[]
    ) {
        this.toDelete = this.toDelete || false;
        this.unique = this.unique || false;
    }
}
