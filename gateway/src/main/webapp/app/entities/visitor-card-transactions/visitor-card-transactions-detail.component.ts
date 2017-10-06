import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorCardTransactions } from './visitor-card-transactions.model';
import { VisitorCardTransactionsService } from './visitor-card-transactions.service';

@Component({
    selector: 'jhi-visitor-card-transactions-detail',
    templateUrl: './visitor-card-transactions-detail.component.html'
})
export class VisitorCardTransactionsDetailComponent implements OnInit, OnDestroy {

    visitorCardTransactions: VisitorCardTransactions;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private visitorCardTransactionsService: VisitorCardTransactionsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVisitorCardTransactions();
    }

    load(id) {
        this.visitorCardTransactionsService.find(id).subscribe((visitorCardTransactions) => {
            this.visitorCardTransactions = visitorCardTransactions;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVisitorCardTransactions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'visitorCardTransactionsListModification',
            (response) => this.load(this.visitorCardTransactions.id)
        );
    }
}
