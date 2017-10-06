import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Commissions } from './commissions.model';
import { CommissionsPopupService } from './commissions-popup.service';
import { CommissionsService } from './commissions.service';
import { Retailers, RetailersService } from '../retailers';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-commissions-dialog',
    templateUrl: './commissions-dialog.component.html'
})
export class CommissionsDialogComponent implements OnInit {

    commissions: Commissions;
    isSaving: boolean;

    retailers: Retailers[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private commissionsService: CommissionsService,
        private retailersService: RetailersService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.retailersService.query()
            .subscribe((res: ResponseWrapper) => { this.retailers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commissions.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commissionsService.update(this.commissions));
        } else {
            this.subscribeToSaveResponse(
                this.commissionsService.create(this.commissions));
        }
    }

    private subscribeToSaveResponse(result: Observable<Commissions>) {
        result.subscribe((res: Commissions) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Commissions) {
        this.eventManager.broadcast({ name: 'commissionsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackRetailersById(index: number, item: Retailers) {
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
    selector: 'jhi-commissions-popup',
    template: ''
})
export class CommissionsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commissionsPopupService: CommissionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commissionsPopupService
                    .open(CommissionsDialogComponent as Component, params['id']);
            } else {
                this.commissionsPopupService
                    .open(CommissionsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
