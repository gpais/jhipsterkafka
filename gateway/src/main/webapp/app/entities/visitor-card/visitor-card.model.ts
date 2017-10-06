import { BaseEntity } from './../../shared';

export class VisitorCard implements BaseEntity {
    constructor(
        public id?: number,
        public tfscNumber?: string,
        public registeredDate?: string,
        public termsAndConditions?: boolean,
        public cards?: BaseEntity[],
        public visitor?: BaseEntity,
    ) {
        this.termsAndConditions = false;
    }
}
