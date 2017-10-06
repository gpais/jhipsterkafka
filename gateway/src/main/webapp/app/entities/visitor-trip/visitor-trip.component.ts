import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { VisitorTrip } from './visitor-trip.model';
import { VisitorTripService } from './visitor-trip.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-visitor-trip',
    templateUrl: './visitor-trip.component.html'
})
export class VisitorTripComponent implements OnInit, OnDestroy {
visitorTrips: VisitorTrip[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private visitorTripService: VisitorTripService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.visitorTripService.query().subscribe(
            (res: ResponseWrapper) => {
                this.visitorTrips = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVisitorTrips();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VisitorTrip) {
        return item.id;
    }
    registerChangeInVisitorTrips() {
        this.eventSubscriber = this.eventManager.subscribe('visitorTripListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
