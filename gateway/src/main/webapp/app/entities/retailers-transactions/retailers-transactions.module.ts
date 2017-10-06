import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    RetailersTransactionsService,
    RetailersTransactionsPopupService,
    RetailersTransactionsComponent,
    RetailersTransactionsDetailComponent,
    RetailersTransactionsDialogComponent,
    RetailersTransactionsPopupComponent,
    RetailersTransactionsDeletePopupComponent,
    RetailersTransactionsDeleteDialogComponent,
    retailersTransactionsRoute,
    retailersTransactionsPopupRoute,
    RetailersTransactionsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...retailersTransactionsRoute,
    ...retailersTransactionsPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RetailersTransactionsComponent,
        RetailersTransactionsDetailComponent,
        RetailersTransactionsDialogComponent,
        RetailersTransactionsDeleteDialogComponent,
        RetailersTransactionsPopupComponent,
        RetailersTransactionsDeletePopupComponent,
    ],
    entryComponents: [
        RetailersTransactionsComponent,
        RetailersTransactionsDialogComponent,
        RetailersTransactionsPopupComponent,
        RetailersTransactionsDeleteDialogComponent,
        RetailersTransactionsDeletePopupComponent,
    ],
    providers: [
        RetailersTransactionsService,
        RetailersTransactionsPopupService,
        RetailersTransactionsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayRetailersTransactionsModule {}
