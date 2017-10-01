import { BaseEntity } from './../../shared';

export const enum TransactionStatus {
    'NOT_MATCHED',
    'MATCHED',
    'SUSPENDED',
    'SETTLED',
    'REFUNDED'
}

export class Transaction implements BaseEntity {
    constructor(
        public id?: number,
        public retailerNo?: string,
        public tfscNumber?: string,
        public voucherNo?: string,
        public creditCardNo?: string,
        public creditCardExpiry?: string,
        public purchaseDate?: any,
        public purchaseTime?: string,
        public vatOff?: string,
        public vatAmount?: number,
        public grossAmount?: number,
        public refundAmount?: number,
        public vatRate?: number,
        public status?: TransactionStatus,
        public reason?: string,
    ) {
    }
}
