import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayTransactionModule } from './transaction/transaction.module';
import { GatewayCommissionsModule } from './commissions/commissions.module';
import { GatewayRetailersModule } from './retailers/retailers.module';
import { GatewayLocationModule } from './location/location.module';
import { GatewayRetailersTransactionsModule } from './retailers-transactions/retailers-transactions.module';
import { GatewayVisitorModule } from './visitor/visitor.module';
import { GatewayVisitorCardModule } from './visitor-card/visitor-card.module';
import { GatewayVisitorCardTransactionsModule } from './visitor-card-transactions/visitor-card-transactions.module';
import { GatewayVisitorQueriesModule } from './visitor-queries/visitor-queries.module';
import { GatewayVisitorTripModule } from './visitor-trip/visitor-trip.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GatewayTransactionModule,
        GatewayCommissionsModule,
        GatewayRetailersModule,
        GatewayLocationModule,
        GatewayRetailersTransactionsModule,
        GatewayVisitorModule,
        GatewayVisitorCardModule,
        GatewayVisitorCardTransactionsModule,
        GatewayVisitorQueriesModule,
        GatewayVisitorTripModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
