import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Visitor } from './visitor.model';
import { VisitorService } from './visitor.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-visitor',
    templateUrl: './visitor.component.html'
})
export class VisitorComponent implements OnInit, OnDestroy {
visitors: Visitor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private visitorService: VisitorService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.visitorService.query().subscribe(
            (res: ResponseWrapper) => {
                this.visitors = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVisitors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Visitor) {
        return item.id;
    }
    registerChangeInVisitors() {
        this.eventSubscriber = this.eventManager.subscribe('visitorListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
