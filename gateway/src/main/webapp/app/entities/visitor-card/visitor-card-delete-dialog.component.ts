import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorCard } from './visitor-card.model';
import { VisitorCardPopupService } from './visitor-card-popup.service';
import { VisitorCardService } from './visitor-card.service';

@Component({
    selector: 'jhi-visitor-card-delete-dialog',
    templateUrl: './visitor-card-delete-dialog.component.html'
})
export class VisitorCardDeleteDialogComponent {

    visitorCard: VisitorCard;

    constructor(
        private visitorCardService: VisitorCardService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visitorCardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'visitorCardListModification',
                content: 'Deleted an visitorCard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visitor-card-delete-popup',
    template: ''
})
export class VisitorCardDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorCardPopupService: VisitorCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.visitorCardPopupService
                .open(VisitorCardDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
