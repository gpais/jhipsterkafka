import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorQueries } from './visitor-queries.model';
import { VisitorQueriesService } from './visitor-queries.service';

@Component({
    selector: 'jhi-visitor-queries-detail',
    templateUrl: './visitor-queries-detail.component.html'
})
export class VisitorQueriesDetailComponent implements OnInit, OnDestroy {

    visitorQueries: VisitorQueries;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private visitorQueriesService: VisitorQueriesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVisitorQueries();
    }

    load(id) {
        this.visitorQueriesService.find(id).subscribe((visitorQueries) => {
            this.visitorQueries = visitorQueries;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVisitorQueries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'visitorQueriesListModification',
            (response) => this.load(this.visitorQueries.id)
        );
    }
}
