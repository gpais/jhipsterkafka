import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VisitorTrip } from './visitor-trip.model';
import { VisitorTripPopupService } from './visitor-trip-popup.service';
import { VisitorTripService } from './visitor-trip.service';
import { Visitor, VisitorService } from '../visitor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-visitor-trip-dialog',
    templateUrl: './visitor-trip-dialog.component.html'
})
export class VisitorTripDialogComponent implements OnInit {

    visitorTrip: VisitorTrip;
    isSaving: boolean;

    visitors: Visitor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private visitorTripService: VisitorTripService,
        private visitorService: VisitorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.visitorService.query()
            .subscribe((res: ResponseWrapper) => { this.visitors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.visitorTrip.id !== undefined) {
            this.subscribeToSaveResponse(
                this.visitorTripService.update(this.visitorTrip));
        } else {
            this.subscribeToSaveResponse(
                this.visitorTripService.create(this.visitorTrip));
        }
    }

    private subscribeToSaveResponse(result: Observable<VisitorTrip>) {
        result.subscribe((res: VisitorTrip) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VisitorTrip) {
        this.eventManager.broadcast({ name: 'visitorTripListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackVisitorById(index: number, item: Visitor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-visitor-trip-popup',
    template: ''
})
export class VisitorTripPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorTripPopupService: VisitorTripPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.visitorTripPopupService
                    .open(VisitorTripDialogComponent as Component, params['id']);
            } else {
                this.visitorTripPopupService
                    .open(VisitorTripDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
