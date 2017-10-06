import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Retailers } from './retailers.model';
import { RetailersService } from './retailers.service';

@Component({
    selector: 'jhi-retailers-detail',
    templateUrl: './retailers-detail.component.html'
})
export class RetailersDetailComponent implements OnInit, OnDestroy {

    retailers: Retailers;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private retailersService: RetailersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRetailers();
    }

    load(id) {
        this.retailersService.find(id).subscribe((retailers) => {
            this.retailers = retailers;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRetailers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'retailersListModification',
            (response) => this.load(this.retailers.id)
        );
    }
}
