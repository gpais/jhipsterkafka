import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { VisitorQueries } from './visitor-queries.model';
import { VisitorQueriesService } from './visitor-queries.service';

@Injectable()
export class VisitorQueriesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private visitorQueriesService: VisitorQueriesService

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
                this.visitorQueriesService.find(id).subscribe((visitorQueries) => {
                    visitorQueries.queryDate = this.datePipe
                        .transform(visitorQueries.queryDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.visitorQueriesModalRef(component, visitorQueries);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.visitorQueriesModalRef(component, new VisitorQueries());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    visitorQueriesModalRef(component: Component, visitorQueries: VisitorQueries): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.visitorQueries = visitorQueries;
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
