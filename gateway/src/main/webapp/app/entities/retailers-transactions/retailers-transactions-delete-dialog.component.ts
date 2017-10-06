import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RetailersTransactions } from './retailers-transactions.model';
import { RetailersTransactionsPopupService } from './retailers-transactions-popup.service';
import { RetailersTransactionsService } from './retailers-transactions.service';

@Component({
    selector: 'jhi-retailers-transactions-delete-dialog',
    templateUrl: './retailers-transactions-delete-dialog.component.html'
})
export class RetailersTransactionsDeleteDialogComponent {

    retailersTransactions: RetailersTransactions;

    constructor(
        private retailersTransactionsService: RetailersTransactionsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.retailersTransactionsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'retailersTransactionsListModification',
                content: 'Deleted an retailersTransactions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-retailers-transactions-delete-popup',
    template: ''
})
export class RetailersTransactionsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private retailersTransactionsPopupService: RetailersTransactionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.retailersTransactionsPopupService
                .open(RetailersTransactionsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
