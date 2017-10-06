import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VisitorQueries } from './visitor-queries.model';
import { VisitorQueriesPopupService } from './visitor-queries-popup.service';
import { VisitorQueriesService } from './visitor-queries.service';
import { Visitor, VisitorService } from '../visitor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-visitor-queries-dialog',
    templateUrl: './visitor-queries-dialog.component.html'
})
export class VisitorQueriesDialogComponent implements OnInit {

    visitorQueries: VisitorQueries;
    isSaving: boolean;

    visitors: Visitor[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private visitorQueriesService: VisitorQueriesService,
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
        if (this.visitorQueries.id !== undefined) {
            this.subscribeToSaveResponse(
                this.visitorQueriesService.update(this.visitorQueries));
        } else {
            this.subscribeToSaveResponse(
                this.visitorQueriesService.create(this.visitorQueries));
        }
    }

    private subscribeToSaveResponse(result: Observable<VisitorQueries>) {
        result.subscribe((res: VisitorQueries) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VisitorQueries) {
        this.eventManager.broadcast({ name: 'visitorQueriesListModification', content: 'OK'});
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
    selector: 'jhi-visitor-queries-popup',
    template: ''
})
export class VisitorQueriesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorQueriesPopupService: VisitorQueriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.visitorQueriesPopupService
                    .open(VisitorQueriesDialogComponent as Component, params['id']);
            } else {
                this.visitorQueriesPopupService
                    .open(VisitorQueriesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
