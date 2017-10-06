import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VisitorCardTransactionsComponent } from './visitor-card-transactions.component';
import { VisitorCardTransactionsDetailComponent } from './visitor-card-transactions-detail.component';
import { VisitorCardTransactionsPopupComponent } from './visitor-card-transactions-dialog.component';
import { VisitorCardTransactionsDeletePopupComponent } from './visitor-card-transactions-delete-dialog.component';

@Injectable()
export class VisitorCardTransactionsResolvePagingParams implements Resolve<any> {

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

export const visitorCardTransactionsRoute: Routes = [
    {
        path: 'visitor-card-transactions',
        component: VisitorCardTransactionsComponent,
        resolve: {
            'pagingParams': VisitorCardTransactionsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCardTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'visitor-card-transactions/:id',
        component: VisitorCardTransactionsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCardTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitorCardTransactionsPopupRoute: Routes = [
    {
        path: 'visitor-card-transactions-new',
        component: VisitorCardTransactionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCardTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-card-transactions/:id/edit',
        component: VisitorCardTransactionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCardTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-card-transactions/:id/delete',
        component: VisitorCardTransactionsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCardTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
