import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CommissionsComponent } from './commissions.component';
import { CommissionsDetailComponent } from './commissions-detail.component';
import { CommissionsPopupComponent } from './commissions-dialog.component';
import { CommissionsDeletePopupComponent } from './commissions-delete-dialog.component';

export const commissionsRoute: Routes = [
    {
        path: 'commissions',
        component: CommissionsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.commissions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commissions/:id',
        component: CommissionsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.commissions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commissionsPopupRoute: Routes = [
    {
        path: 'commissions-new',
        component: CommissionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.commissions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commissions/:id/edit',
        component: CommissionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.commissions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commissions/:id/delete',
        component: CommissionsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.commissions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
