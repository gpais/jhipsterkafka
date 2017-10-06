import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorQueries } from './visitor-queries.model';
import { VisitorQueriesPopupService } from './visitor-queries-popup.service';
import { VisitorQueriesService } from './visitor-queries.service';

@Component({
    selector: 'jhi-visitor-queries-delete-dialog',
    templateUrl: './visitor-queries-delete-dialog.component.html'
})
export class VisitorQueriesDeleteDialogComponent {

    visitorQueries: VisitorQueries;

    constructor(
        private visitorQueriesService: VisitorQueriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visitorQueriesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'visitorQueriesListModification',
                content: 'Deleted an visitorQueries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visitor-queries-delete-popup',
    template: ''
})
export class VisitorQueriesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitorQueriesPopupService: VisitorQueriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.visitorQueriesPopupService
                .open(VisitorQueriesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
