import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VisitorCardComponent } from './visitor-card.component';
import { VisitorCardDetailComponent } from './visitor-card-detail.component';
import { VisitorCardPopupComponent } from './visitor-card-dialog.component';
import { VisitorCardDeletePopupComponent } from './visitor-card-delete-dialog.component';

export const visitorCardRoute: Routes = [
    {
        path: 'visitor-card',
        component: VisitorCardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'visitor-card/:id',
        component: VisitorCardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitorCardPopupRoute: Routes = [
    {
        path: 'visitor-card-new',
        component: VisitorCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-card/:id/edit',
        component: VisitorCardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-card/:id/delete',
        component: VisitorCardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
