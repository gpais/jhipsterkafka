import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RetailersTransactions } from './retailers-transactions.model';
import { RetailersTransactionsService } from './retailers-transactions.service';

@Component({
    selector: 'jhi-retailers-transactions-detail',
    templateUrl: './retailers-transactions-detail.component.html'
})
export class RetailersTransactionsDetailComponent implements OnInit, OnDestroy {

    retailersTransactions: RetailersTransactions;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private retailersTransactionsService: RetailersTransactionsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRetailersTransactions();
    }

    load(id) {
        this.retailersTransactionsService.find(id).subscribe((retailersTransactions) => {
            this.retailersTransactions = retailersTransactions;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRetailersTransactions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'retailersTransactionsListModification',
            (response) => this.load(this.retailersTransactions.id)
        );
    }
}
