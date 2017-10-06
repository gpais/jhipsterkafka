import { BaseEntity } from './../../shared';

export class VisitorQueries implements BaseEntity {
    constructor(
        public id?: number,
        public queryDate?: any,
        public queryDescription?: string,
        public processDate?: string,
        public salutation?: string,
        public visitor?: BaseEntity,
    ) {
    }
}
