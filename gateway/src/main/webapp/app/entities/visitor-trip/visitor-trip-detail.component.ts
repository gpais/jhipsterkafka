import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorTrip } from './visitor-trip.model';
import { VisitorTripService } from './visitor-trip.service';

@Component({
    selector: 'jhi-visitor-trip-detail',
    templateUrl: './visitor-trip-detail.component.html'
})
export class VisitorTripDetailComponent implements OnInit, OnDestroy {

    visitorTrip: VisitorTrip;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private visitorTripService: VisitorTripService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVisitorTrips();
    }

    load(id) {
        this.visitorTripService.find(id).subscribe((visitorTrip) => {
            this.visitorTrip = visitorTrip;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVisitorTrips() {
        this.eventSubscriber = this.eventManager.subscribe(
            'visitorTripListModification',
            (response) => this.load(this.visitorTrip.id)
        );
    }
}
