export interface ISimpleItem {
    id?: string;
    startPos?: number;
    endPos?: number;
}

export class SimpleItem implements ISimpleItem {
    constructor(public id?: string, public startPos?: number, public endPos?: number) {}
}
