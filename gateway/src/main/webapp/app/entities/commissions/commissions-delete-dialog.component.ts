import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Commissions } from './commissions.model';
import { CommissionsPopupService } from './commissions-popup.service';
import { CommissionsService } from './commissions.service';

@Component({
    selector: 'jhi-commissions-delete-dialog',
    templateUrl: './commissions-delete-dialog.component.html'
})
export class CommissionsDeleteDialogComponent {

    commissions: Commissions;

    constructor(
        private commissionsService: CommissionsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commissionsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commissionsListModification',
                content: 'Deleted an commissions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commissions-delete-popup',
    template: ''
})
export class CommissionsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commissionsPopupService: CommissionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commissionsPopupService
                .open(CommissionsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
