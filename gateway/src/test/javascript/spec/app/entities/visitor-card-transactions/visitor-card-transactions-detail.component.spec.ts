/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisitorCardTransactionsDetailComponent } from '../../../../../../main/webapp/app/entities/visitor-card-transactions/visitor-card-transactions-detail.component';
import { VisitorCardTransactionsService } from '../../../../../../main/webapp/app/entities/visitor-card-transactions/visitor-card-transactions.service';
import { VisitorCardTransactions } from '../../../../../../main/webapp/app/entities/visitor-card-transactions/visitor-card-transactions.model';

describe('Component Tests', () => {

    describe('VisitorCardTransactions Management Detail Component', () => {
        let comp: VisitorCardTransactionsDetailComponent;
        let fixture: ComponentFixture<VisitorCardTransactionsDetailComponent>;
        let service: VisitorCardTransactionsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [VisitorCardTransactionsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisitorCardTransactionsService,
                    JhiEventManager
                ]
            }).overrideTemplate(VisitorCardTransactionsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisitorCardTransactionsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitorCardTransactionsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VisitorCardTransactions(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visitorCardTransactions).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
