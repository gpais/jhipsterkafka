import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VisitorCardTransactions } from './visitor-card-transactions.model';
import { VisitorCardTransactionsPopupService } from './visitor-card-transactions-popup.service';
import { VisitorCardTransactionsService } from './visitor-card-transactions.service';
import { VisitorCard, VisitorCardService } from '../visitor-card';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-visitor-card-transactions-dialog',
    templateUrl: './visitor-card-transactions-dialog.component.html'
})
export class VisitorCardTransactionsDialogComponent implements OnInit {

    visitorCardTransactions: VisitorCardTransactions;
    isSaving: boolean;

    visitorcards: VisitorCard[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private visitorCardTransactionsService: VisitorCardTransactionsService,
        private visitorCardService: VisitorCardService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.visitorCardService.query()
            .subscribe((res: ResponseWrapper) => { this.visitorcards = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.visitorCardTransactions.id !== undefined) {
            this.subscribeToSaveResponse(
                this.visitorCardTransactionsService.update(this.visitorCardTransactions));
        } else {
            this.subscribeToSaveResponse(
                this.visitorCardTransactionsService.create(this.visitorCardTransactions));
        }
    }

    private subscribeToSaveResponse(result: Observable<VisitorCardTransactions>) {
        result.subscribe((res: VisitorCardTransactions) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VisitorCardTransactions) {
        this.eventManager.broadcast({ name: 'visitorCardTransactionsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackVisitorCardById(index: number, item: VisitorCard) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-visitor-card-transactions-popup',
    template: ''
})
export class VisitorCardTransactionsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorCardTransactionsPopupService: VisitorCardTransactionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.visitorCardTransactionsPopupService
                    .open(VisitorCardTransactionsDialogComponent as Component, params['id']);
            } else {
                this.visitorCardTransactionsPopupService
                    .open(VisitorCardTransactionsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
