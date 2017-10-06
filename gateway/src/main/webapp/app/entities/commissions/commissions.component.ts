import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Commissions } from './commissions.model';
import { CommissionsService } from './commissions.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-commissions',
    templateUrl: './commissions.component.html'
})
export class CommissionsComponent implements OnInit, OnDestroy {
commissions: Commissions[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commissionsService: CommissionsService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.commissionsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.commissions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCommissions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Commissions) {
        return item.id;
    }
    registerChangeInCommissions() {
        this.eventSubscriber = this.eventManager.subscribe('commissionsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
