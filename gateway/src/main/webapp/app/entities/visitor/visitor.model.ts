import { BaseEntity } from './../../shared';

export class Visitor implements BaseEntity {
    constructor(
        public id?: number,
        public surname?: string,
        public forename?: string,
        public address1?: string,
        public address2?: string,
        public address3?: string,
        public address4?: string,
        public postCode?: string,
        public countryId?: string,
        public telephoneNo?: string,
        public emailAddress?: string,
        public passportNumber?: string,
        public cards?: BaseEntity[],
        public trips?: BaseEntity[],
        public queries?: BaseEntity[],
    ) {
    }
}
