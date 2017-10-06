import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { VisitorCard } from './visitor-card.model';
import { VisitorCardService } from './visitor-card.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-visitor-card',
    templateUrl: './visitor-card.component.html'
})
export class VisitorCardComponent implements OnInit, OnDestroy {
visitorCards: VisitorCard[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private visitorCardService: VisitorCardService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.visitorCardService.query().subscribe(
            (res: ResponseWrapper) => {
                this.visitorCards = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVisitorCards();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VisitorCard) {
        return item.id;
    }
    registerChangeInVisitorCards() {
        this.eventSubscriber = this.eventManager.subscribe('visitorCardListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
