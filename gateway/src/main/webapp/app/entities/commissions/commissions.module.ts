import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    CommissionsService,
    CommissionsPopupService,
    CommissionsComponent,
    CommissionsDetailComponent,
    CommissionsDialogComponent,
    CommissionsPopupComponent,
    CommissionsDeletePopupComponent,
    CommissionsDeleteDialogComponent,
    commissionsRoute,
    commissionsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...commissionsRoute,
    ...commissionsPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CommissionsComponent,
        CommissionsDetailComponent,
        CommissionsDialogComponent,
        CommissionsDeleteDialogComponent,
        CommissionsPopupComponent,
        CommissionsDeletePopupComponent,
    ],
    entryComponents: [
        CommissionsComponent,
        CommissionsDialogComponent,
        CommissionsPopupComponent,
        CommissionsDeleteDialogComponent,
        CommissionsDeletePopupComponent,
    ],
    providers: [
        CommissionsService,
        CommissionsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayCommissionsModule {}
