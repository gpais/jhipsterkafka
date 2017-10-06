import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Retailers } from './retailers.model';
import { RetailersService } from './retailers.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-retailers',
    templateUrl: './retailers.component.html'
})
export class RetailersComponent implements OnInit, OnDestroy {
retailers: Retailers[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private retailersService: RetailersService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.retailersService.query().subscribe(
            (res: ResponseWrapper) => {
                this.retailers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRetailers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Retailers) {
        return item.id;
    }
    registerChangeInRetailers() {
        this.eventSubscriber = this.eventManager.subscribe('retailersListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
