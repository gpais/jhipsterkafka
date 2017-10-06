import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Commissions } from './commissions.model';
import { CommissionsService } from './commissions.service';

@Component({
    selector: 'jhi-commissions-detail',
    templateUrl: './commissions-detail.component.html'
})
export class CommissionsDetailComponent implements OnInit, OnDestroy {

    commissions: Commissions;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commissionsService: CommissionsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommissions();
    }

    load(id) {
        this.commissionsService.find(id).subscribe((commissions) => {
            this.commissions = commissions;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommissions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commissionsListModification',
            (response) => this.load(this.commissions.id)
        );
    }
}
