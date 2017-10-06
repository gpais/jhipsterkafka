import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VisitorCardTransactions } from './visitor-card-transactions.model';
import { VisitorCardTransactionsService } from './visitor-card-transactions.service';

@Injectable()
export class VisitorCardTransactionsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private visitorCardTransactionsService: VisitorCardTransactionsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.visitorCardTransactionsService.find(id).subscribe((visitorCardTransactions) => {
                    this.ngbModalRef = this.visitorCardTransactionsModalRef(component, visitorCardTransactions);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.visitorCardTransactionsModalRef(component, new VisitorCardTransactions());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    visitorCardTransactionsModalRef(component: Component, visitorCardTransactions: VisitorCardTransactions): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.visitorCardTransactions = visitorCardTransactions;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
