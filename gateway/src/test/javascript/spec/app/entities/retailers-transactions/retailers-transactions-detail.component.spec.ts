/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RetailersTransactionsDetailComponent } from '../../../../../../main/webapp/app/entities/retailers-transactions/retailers-transactions-detail.component';
import { RetailersTransactionsService } from '../../../../../../main/webapp/app/entities/retailers-transactions/retailers-transactions.service';
import { RetailersTransactions } from '../../../../../../main/webapp/app/entities/retailers-transactions/retailers-transactions.model';

describe('Component Tests', () => {

    describe('RetailersTransactions Management Detail Component', () => {
        let comp: RetailersTransactionsDetailComponent;
        let fixture: ComponentFixture<RetailersTransactionsDetailComponent>;
        let service: RetailersTransactionsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [RetailersTransactionsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RetailersTransactionsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RetailersTransactionsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RetailersTransactionsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RetailersTransactionsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RetailersTransactions(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.retailersTransactions).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
