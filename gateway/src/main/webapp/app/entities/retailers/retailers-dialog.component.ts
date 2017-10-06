import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Retailers } from './retailers.model';
import { RetailersPopupService } from './retailers-popup.service';
import { RetailersService } from './retailers.service';
import { Location, LocationService } from '../location';
import { Commissions, CommissionsService } from '../commissions';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-retailers-dialog',
    templateUrl: './retailers-dialog.component.html'
})
export class RetailersDialogComponent implements OnInit {

    retailers: Retailers;
    isSaving: boolean;

    locations: Location[];

    commissions: Commissions[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private retailersService: RetailersService,
        private locationService: LocationService,
        private commissionsService: CommissionsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService.query()
            .subscribe((res: ResponseWrapper) => { this.locations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.commissionsService.query()
            .subscribe((res: ResponseWrapper) => { this.commissions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.retailers.id !== undefined) {
            this.subscribeToSaveResponse(
                this.retailersService.update(this.retailers));
        } else {
            this.subscribeToSaveResponse(
                this.retailersService.create(this.retailers));
        }
    }

    private subscribeToSaveResponse(result: Observable<Retailers>) {
        result.subscribe((res: Retailers) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Retailers) {
        this.eventManager.broadcast({ name: 'retailersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }

    trackCommissionsById(index: number, item: Commissions) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-retailers-popup',
    template: ''
})
export class RetailersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private retailersPopupService: RetailersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.retailersPopupService
                    .open(RetailersDialogComponent as Component, params['id']);
            } else {
                this.retailersPopupService
                    .open(RetailersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
