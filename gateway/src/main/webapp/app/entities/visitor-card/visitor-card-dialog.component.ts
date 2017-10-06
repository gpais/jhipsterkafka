import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VisitorCard } from './visitor-card.model';
import { VisitorCardPopupService } from './visitor-card-popup.service';
import { VisitorCardService } from './visitor-card.service';
import { Visitor, VisitorService } from '../visitor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-visitor-card-dialog',
    templateUrl: './visitor-card-dialog.component.html'
})
export class VisitorCardDialogComponent implements OnInit {

    visitorCard: VisitorCard;
    isSaving: boolean;

    visitors: Visitor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private visitorCardService: VisitorCardService,
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
        if (this.visitorCard.id !== undefined) {
            this.subscribeToSaveResponse(
                this.visitorCardService.update(this.visitorCard));
        } else {
            this.subscribeToSaveResponse(
                this.visitorCardService.create(this.visitorCard));
        }
    }

    private subscribeToSaveResponse(result: Observable<VisitorCard>) {
        result.subscribe((res: VisitorCard) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VisitorCard) {
        this.eventManager.broadcast({ name: 'visitorCardListModification', content: 'OK'});
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
    selector: 'jhi-visitor-card-popup',
    template: ''
})
export class VisitorCardPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorCardPopupService: VisitorCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.visitorCardPopupService
                    .open(VisitorCardDialogComponent as Component, params['id']);
            } else {
                this.visitorCardPopupService
                    .open(VisitorCardDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
