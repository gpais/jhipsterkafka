import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorTrip } from './visitor-trip.model';
import { VisitorTripPopupService } from './visitor-trip-popup.service';
import { VisitorTripService } from './visitor-trip.service';

@Component({
    selector: 'jhi-visitor-trip-delete-dialog',
    templateUrl: './visitor-trip-delete-dialog.component.html'
})
export class VisitorTripDeleteDialogComponent {

    visitorTrip: VisitorTrip;

    constructor(
        private visitorTripService: VisitorTripService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visitorTripService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'visitorTripListModification',
                content: 'Deleted an visitorTrip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visitor-trip-delete-popup',
    template: ''
})
export class VisitorTripDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorTripPopupService: VisitorTripPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.visitorTripPopupService
                .open(VisitorTripDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
