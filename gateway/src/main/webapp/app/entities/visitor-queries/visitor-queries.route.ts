import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VisitorQueriesComponent } from './visitor-queries.component';
import { VisitorQueriesDetailComponent } from './visitor-queries-detail.component';
import { VisitorQueriesPopupComponent } from './visitor-queries-dialog.component';
import { VisitorQueriesDeletePopupComponent } from './visitor-queries-delete-dialog.component';

export const visitorQueriesRoute: Routes = [
    {
        path: 'visitor-queries',
        component: VisitorQueriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorQueries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'visitor-queries/:id',
        component: VisitorQueriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorQueries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitorQueriesPopupRoute: Routes = [
    {
        path: 'visitor-queries-new',
        component: VisitorQueriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorQueries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-queries/:id/edit',
        component: VisitorQueriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorQueries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-queries/:id/delete',
        component: VisitorQueriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorQueries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
