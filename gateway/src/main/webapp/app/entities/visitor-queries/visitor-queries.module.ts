import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    VisitorQueriesService,
    VisitorQueriesPopupService,
    VisitorQueriesComponent,
    VisitorQueriesDetailComponent,
    VisitorQueriesDialogComponent,
    VisitorQueriesPopupComponent,
    VisitorQueriesDeletePopupComponent,
    VisitorQueriesDeleteDialogComponent,
    visitorQueriesRoute,
    visitorQueriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...visitorQueriesRoute,
    ...visitorQueriesPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisitorQueriesComponent,
        VisitorQueriesDetailComponent,
        VisitorQueriesDialogComponent,
        VisitorQueriesDeleteDialogComponent,
        VisitorQueriesPopupComponent,
        VisitorQueriesDeletePopupComponent,
    ],
    entryComponents: [
        VisitorQueriesComponent,
        VisitorQueriesDialogComponent,
        VisitorQueriesPopupComponent,
        VisitorQueriesDeleteDialogComponent,
        VisitorQueriesDeletePopupComponent,
    ],
    providers: [
        VisitorQueriesService,
        VisitorQueriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayVisitorQueriesModule {}
