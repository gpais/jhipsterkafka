import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    VisitorCardService,
    VisitorCardPopupService,
    VisitorCardComponent,
    VisitorCardDetailComponent,
    VisitorCardDialogComponent,
    VisitorCardPopupComponent,
    VisitorCardDeletePopupComponent,
    VisitorCardDeleteDialogComponent,
    visitorCardRoute,
    visitorCardPopupRoute,
} from './';

const ENTITY_STATES = [
    ...visitorCardRoute,
    ...visitorCardPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisitorCardComponent,
        VisitorCardDetailComponent,
        VisitorCardDialogComponent,
        VisitorCardDeleteDialogComponent,
        VisitorCardPopupComponent,
        VisitorCardDeletePopupComponent,
    ],
    entryComponents: [
        VisitorCardComponent,
        VisitorCardDialogComponent,
        VisitorCardPopupComponent,
        VisitorCardDeleteDialogComponent,
        VisitorCardDeletePopupComponent,
    ],
    providers: [
        VisitorCardService,
        VisitorCardPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayVisitorCardModule {}
