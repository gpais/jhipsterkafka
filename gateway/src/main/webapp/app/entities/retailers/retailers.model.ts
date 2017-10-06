import { BaseEntity } from './../../shared';

export class Retailers implements BaseEntity {
    constructor(
        public id?: number,
        public retailerNo?: string,
        public name?: string,
        public phone?: string,
        public contact1?: string,
        public contact2?: string,
        public contact3?: string,
        public contact4?: string,
        public contact5?: string,
        public taxAmount?: number,
        public minTaxAmount?: number,
        public email?: string,
        public vatNo?: string,
        public location?: BaseEntity,
        public transactions?: BaseEntity[],
        public commissions?: BaseEntity[],
    ) {
    }
}
