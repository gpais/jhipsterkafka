import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RetailersTransactions } from './retailers-transactions.model';
import { RetailersTransactionsPopupService } from './retailers-transactions-popup.service';
import { RetailersTransactionsService } from './retailers-transactions.service';
import { Retailers, RetailersService } from '../retailers';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-retailers-transactions-dialog',
    templateUrl: './retailers-transactions-dialog.component.html'
})
export class RetailersTransactionsDialogComponent implements OnInit {

    retailersTransactions: RetailersTransactions;
    isSaving: boolean;

    retailers: Retailers[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private retailersTransactionsService: RetailersTransactionsService,
        private retailersService: RetailersService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.retailersService.query()
            .subscribe((res: ResponseWrapper) => { this.retailers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.retailersTransactions.id !== undefined) {
            this.subscribeToSaveResponse(
                this.retailersTransactionsService.update(this.retailersTransactions));
        } else {
            this.subscribeToSaveResponse(
                this.retailersTransactionsService.create(this.retailersTransactions));
        }
    }

    private subscribeToSaveResponse(result: Observable<RetailersTransactions>) {
        result.subscribe((res: RetailersTransactions) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RetailersTransactions) {
        this.eventManager.broadcast({ name: 'retailersTransactionsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackRetailersById(index: number, item: Retailers) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-retailers-transactions-popup',
    template: ''
})
export class RetailersTransactionsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private retailersTransactionsPopupService: RetailersTransactionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.retailersTransactionsPopupService
                    .open(RetailersTransactionsDialogComponent as Component, params['id']);
            } else {
                this.retailersTransactionsPopupService
                    .open(RetailersTransactionsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
