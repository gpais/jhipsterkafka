import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Retailers } from './retailers.model';
import { RetailersPopupService } from './retailers-popup.service';
import { RetailersService } from './retailers.service';

@Component({
    selector: 'jhi-retailers-delete-dialog',
    templateUrl: './retailers-delete-dialog.component.html'
})
export class RetailersDeleteDialogComponent {

    retailers: Retailers;

    constructor(
        private retailersService: RetailersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.retailersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'retailersListModification',
                content: 'Deleted an retailers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-retailers-delete-popup',
    template: ''
})
export class RetailersDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private retailersPopupService: RetailersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.retailersPopupService
                .open(RetailersDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
