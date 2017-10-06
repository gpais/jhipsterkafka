import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RetailersTransactionsComponent } from './retailers-transactions.component';
import { RetailersTransactionsDetailComponent } from './retailers-transactions-detail.component';
import { RetailersTransactionsPopupComponent } from './retailers-transactions-dialog.component';
import { RetailersTransactionsDeletePopupComponent } from './retailers-transactions-delete-dialog.component';

@Injectable()
export class RetailersTransactionsResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const retailersTransactionsRoute: Routes = [
    {
        path: 'retailers-transactions',
        component: RetailersTransactionsComponent,
        resolve: {
            'pagingParams': RetailersTransactionsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailersTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'retailers-transactions/:id',
        component: RetailersTransactionsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailersTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const retailersTransactionsPopupRoute: Routes = [
    {
        path: 'retailers-transactions-new',
        component: RetailersTransactionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailersTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'retailers-transactions/:id/edit',
        component: RetailersTransactionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailersTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'retailers-transactions/:id/delete',
        component: RetailersTransactionsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailersTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
