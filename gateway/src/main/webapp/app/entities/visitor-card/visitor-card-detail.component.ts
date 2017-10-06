import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VisitorCard } from './visitor-card.model';
import { VisitorCardService } from './visitor-card.service';

@Component({
    selector: 'jhi-visitor-card-detail',
    templateUrl: './visitor-card-detail.component.html'
})
export class VisitorCardDetailComponent implements OnInit, OnDestroy {

    visitorCard: VisitorCard;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private visitorCardService: VisitorCardService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVisitorCards();
    }

    load(id) {
        this.visitorCardService.find(id).subscribe((visitorCard) => {
            this.visitorCard = visitorCard;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVisitorCards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'visitorCardListModification',
            (response) => this.load(this.visitorCard.id)
        );
    }
}
