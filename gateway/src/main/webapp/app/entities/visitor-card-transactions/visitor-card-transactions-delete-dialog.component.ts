import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorCardTransactions } from './visitor-card-transactions.model';
import { VisitorCardTransactionsPopupService } from './visitor-card-transactions-popup.service';
import { VisitorCardTransactionsService } from './visitor-card-transactions.service';

@Component({
    selector: 'jhi-visitor-card-transactions-delete-dialog',
    templateUrl: './visitor-card-transactions-delete-dialog.component.html'
})
export class VisitorCardTransactionsDeleteDialogComponent {

    visitorCardTransactions: VisitorCardTransactions;

    constructor(
        private visitorCardTransactionsService: VisitorCardTransactionsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visitorCardTransactionsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'visitorCardTransactionsListModification',
                content: 'Deleted an visitorCardTransactions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visitor-card-transactions-delete-popup',
    template: ''
})
export class VisitorCardTransactionsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorCardTransactionsPopupService: VisitorCardTransactionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.visitorCardTransactionsPopupService
                .open(VisitorCardTransactionsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
