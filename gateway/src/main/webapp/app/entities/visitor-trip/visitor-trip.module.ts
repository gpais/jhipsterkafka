import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    VisitorTripService,
    VisitorTripPopupService,
    VisitorTripComponent,
    VisitorTripDetailComponent,
    VisitorTripDialogComponent,
    VisitorTripPopupComponent,
    VisitorTripDeletePopupComponent,
    VisitorTripDeleteDialogComponent,
    visitorTripRoute,
    visitorTripPopupRoute,
} from './';

const ENTITY_STATES = [
    ...visitorTripRoute,
    ...visitorTripPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisitorTripComponent,
        VisitorTripDetailComponent,
        VisitorTripDialogComponent,
        VisitorTripDeleteDialogComponent,
        VisitorTripPopupComponent,
        VisitorTripDeletePopupComponent,
    ],
    entryComponents: [
        VisitorTripComponent,
        VisitorTripDialogComponent,
        VisitorTripPopupComponent,
        VisitorTripDeleteDialogComponent,
        VisitorTripDeletePopupComponent,
    ],
    providers: [
        VisitorTripService,
        VisitorTripPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayVisitorTripModule {}
