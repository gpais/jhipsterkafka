import { BaseEntity } from './../../shared';

export class VisitorTrip implements BaseEntity {
    constructor(
        public id?: number,
        public locationCode?: string,
        public dateEntry?: any,
        public dateExit?: any,
        public flightNumber?: string,
        public creditCardType?: string,
        public creditCardToken?: string,
        public creditCardExpiry?: any,
        public cardHolderName?: string,
        public createdDate?: any,
        public matchLargeTX?: boolean,
        public visitor?: BaseEntity,
    ) {
        this.matchLargeTX = false;
    }
}
