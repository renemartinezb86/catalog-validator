import { ICatalog } from 'app/shared/model//catalog.model';
import { IHierarchy } from 'app/shared/model//hierarchy.model';

export interface IHierarchy {
    id?: string;
    code?: string;
    name?: string;
    type?: string;
    parent?: string;
    catalog?: ICatalog;
    hierarchy?: IHierarchy;
    subHierars?: IHierarchy[];
}

export class Hierarchy implements IHierarchy {
    constructor(
        public id?: string,
        public code?: string,
        public name?: string,
        public type?: string,
        public parent?: string,
        public catalog?: ICatalog,
        public hierarchy?: IHierarchy,
        public subHierars?: IHierarchy[]
    ) {}
}
