import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VisitorTripComponent } from './visitor-trip.component';
import { VisitorTripDetailComponent } from './visitor-trip-detail.component';
import { VisitorTripPopupComponent } from './visitor-trip-dialog.component';
import { VisitorTripDeletePopupComponent } from './visitor-trip-delete-dialog.component';

export const visitorTripRoute: Routes = [
    {
        path: 'visitor-trip',
        component: VisitorTripComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorTrip.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'visitor-trip/:id',
        component: VisitorTripDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorTrip.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitorTripPopupRoute: Routes = [
    {
        path: 'visitor-trip-new',
        component: VisitorTripPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorTrip.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-trip/:id/edit',
        component: VisitorTripPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorTrip.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visitor-trip/:id/delete',
        component: VisitorTripDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.visitorTrip.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
