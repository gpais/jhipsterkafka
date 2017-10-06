import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { VisitorQueries } from './visitor-queries.model';
import { VisitorQueriesService } from './visitor-queries.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-visitor-queries',
    templateUrl: './visitor-queries.component.html'
})
export class VisitorQueriesComponent implements OnInit, OnDestroy {
visitorQueries: VisitorQueries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private visitorQueriesService: VisitorQueriesService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.visitorQueriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.visitorQueries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVisitorQueries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VisitorQueries) {
        return item.id;
    }
    registerChangeInVisitorQueries() {
        this.eventSubscriber = this.eventManager.subscribe('visitorQueriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
