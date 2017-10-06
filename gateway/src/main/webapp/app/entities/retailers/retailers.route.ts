import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RetailersComponent } from './retailers.component';
import { RetailersDetailComponent } from './retailers-detail.component';
import { RetailersPopupComponent } from './retailers-dialog.component';
import { RetailersDeletePopupComponent } from './retailers-delete-dialog.component';

export const retailersRoute: Routes = [
    {
        path: 'retailers',
        component: RetailersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'retailers/:id',
        component: RetailersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const retailersPopupRoute: Routes = [
    {
        path: 'retailers-new',
        component: RetailersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'retailers/:id/edit',
        component: RetailersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'retailers/:id/delete',
        component: RetailersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.retailers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
