import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    VisitorCardTransactionsService,
    VisitorCardTransactionsPopupService,
    VisitorCardTransactionsComponent,
    VisitorCardTransactionsDetailComponent,
    VisitorCardTransactionsDialogComponent,
    VisitorCardTransactionsPopupComponent,
    VisitorCardTransactionsDeletePopupComponent,
    VisitorCardTransactionsDeleteDialogComponent,
    visitorCardTransactionsRoute,
    visitorCardTransactionsPopupRoute,
    VisitorCardTransactionsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...visitorCardTransactionsRoute,
    ...visitorCardTransactionsPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisitorCardTransactionsComponent,
        VisitorCardTransactionsDetailComponent,
        VisitorCardTransactionsDialogComponent,
        VisitorCardTransactionsDeleteDialogComponent,
        VisitorCardTransactionsPopupComponent,
        VisitorCardTransactionsDeletePopupComponent,
    ],
    entryComponents: [
        VisitorCardTransactionsComponent,
        VisitorCardTransactionsDialogComponent,
        VisitorCardTransactionsPopupComponent,
        VisitorCardTransactionsDeleteDialogComponent,
        VisitorCardTransactionsDeletePopupComponent,
    ],
    providers: [
        VisitorCardTransactionsService,
        VisitorCardTransactionsPopupService,
        VisitorCardTransactionsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayVisitorCardTransactionsModule {}
