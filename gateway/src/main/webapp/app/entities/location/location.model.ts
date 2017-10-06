import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public address1?: string,
        public address2?: string,
        public address3?: string,
        public countyCode?: string,
        public latitude?: string,
        public longitude?: string,
        public retailers?: BaseEntity[],
    ) {
    }
}
