import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { VisitorTrip } from './visitor-trip.model';
import { VisitorTripService } from './visitor-trip.service';

@Injectable()
export class VisitorTripPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private visitorTripService: VisitorTripService

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
                this.visitorTripService.find(id).subscribe((visitorTrip) => {
                    visitorTrip.dateEntry = this.datePipe
                        .transform(visitorTrip.dateEntry, 'yyyy-MM-ddTHH:mm:ss');
                    visitorTrip.dateExit = this.datePipe
                        .transform(visitorTrip.dateExit, 'yyyy-MM-ddTHH:mm:ss');
                    visitorTrip.creditCardExpiry = this.datePipe
                        .transform(visitorTrip.creditCardExpiry, 'yyyy-MM-ddTHH:mm:ss');
                    visitorTrip.createdDate = this.datePipe
                        .transform(visitorTrip.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.visitorTripModalRef(component, visitorTrip);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.visitorTripModalRef(component, new VisitorTrip());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    visitorTripModalRef(component: Component, visitorTrip: VisitorTrip): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.visitorTrip = visitorTrip;
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
