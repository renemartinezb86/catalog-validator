import { IItem } from 'app/shared/model//item.model';

export interface IAttribute {
    id?: string;
    name?: string;
    value?: string;
    item?: IItem;
}

export class Attribute implements IAttribute {
    constructor(public id?: string, public name?: string, public value?: string, public item?: IItem) {}
}
