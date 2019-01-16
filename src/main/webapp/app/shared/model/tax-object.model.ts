export interface ITaxObject {
    id?: string;
    taxPercent?: string;
    incInPrice?: string;
}

export class TaxObject implements ITaxObject {
    constructor(public id?: string, public taxPercent?: string, public incInPrice?: string) {}
}
