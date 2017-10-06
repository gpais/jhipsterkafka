import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    RetailersService,
    RetailersPopupService,
    RetailersComponent,
    RetailersDetailComponent,
    RetailersDialogComponent,
    RetailersPopupComponent,
    RetailersDeletePopupComponent,
    RetailersDeleteDialogComponent,
    retailersRoute,
    retailersPopupRoute,
} from './';

const ENTITY_STATES = [
    ...retailersRoute,
    ...retailersPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RetailersComponent,
        RetailersDetailComponent,
        RetailersDialogComponent,
        RetailersDeleteDialogComponent,
        RetailersPopupComponent,
        RetailersDeletePopupComponent,
    ],
    entryComponents: [
        RetailersComponent,
        RetailersDialogComponent,
        RetailersPopupComponent,
        RetailersDeleteDialogComponent,
        RetailersDeletePopupComponent,
    ],
    providers: [
        RetailersService,
        RetailersPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayRetailersModule {}
