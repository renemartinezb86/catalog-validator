export interface IIND {
    id?: string;
    name?: string;
    desc?: string;
    writeToLLD?: boolean;
    writeTranslation?: boolean;
}

export class IND implements IIND {
    constructor(
        public id?: string,
        public name?: string,
        public desc?: string,
        public writeToLLD?: boolean,
        public writeTranslation?: boolean
    ) {
        this.writeToLLD = this.writeToLLD || false;
        this.writeTranslation = this.writeTranslation || false;
    }
}
