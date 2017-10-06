import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RetailersTransactions } from './retailers-transactions.model';
import { RetailersTransactionsService } from './retailers-transactions.service';

@Injectable()
export class RetailersTransactionsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private retailersTransactionsService: RetailersTransactionsService

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
                this.retailersTransactionsService.find(id).subscribe((retailersTransactions) => {
                    this.ngbModalRef = this.retailersTransactionsModalRef(component, retailersTransactions);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.retailersTransactionsModalRef(component, new RetailersTransactions());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    retailersTransactionsModalRef(component: Component, retailersTransactions: RetailersTransactions): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.retailersTransactions = retailersTransactions;
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
