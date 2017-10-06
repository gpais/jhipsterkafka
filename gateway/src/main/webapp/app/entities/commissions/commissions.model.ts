import { BaseEntity } from './../../shared';

export class Commissions implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public retailerCommission?: number,
        public tfsCommission?: number,
        public retailers?: BaseEntity[],
    ) {
    }
}
