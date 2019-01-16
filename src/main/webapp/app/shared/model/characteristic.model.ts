import { IItem } from 'app/shared/model//item.model';

export interface ICharacteristic {
    id?: string;
    charID?: string;
    charName?: string;
    charType?: string;
    defaultValue?: string;
    charClassificationType?: string;
    sequence?: string;
    assocType?: string;
    promoted?: string;
    sourceItem?: string;
    charDefaultRule?: string;
    mapto?: string;
    item?: IItem;
}

export class Characteristic implements ICharacteristic {
    constructor(
        public id?: string,
        public charID?: string,
        public charName?: string,
        public charType?: string,
        public defaultValue?: string,
        public charClassificationType?: string,
        public sequence?: string,
        public assocType?: string,
        public promoted?: string,
        public sourceItem?: string,
        public charDefaultRule?: string,
        public mapto?: string,
        public item?: IItem
    ) {}
}
