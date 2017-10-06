/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisitorCardDetailComponent } from '../../../../../../main/webapp/app/entities/visitor-card/visitor-card-detail.component';
import { VisitorCardService } from '../../../../../../main/webapp/app/entities/visitor-card/visitor-card.service';
import { VisitorCard } from '../../../../../../main/webapp/app/entities/visitor-card/visitor-card.model';

describe('Component Tests', () => {

    describe('VisitorCard Management Detail Component', () => {
        let comp: VisitorCardDetailComponent;
        let fixture: ComponentFixture<VisitorCardDetailComponent>;
        let service: VisitorCardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [VisitorCardDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisitorCardService,
                    JhiEventManager
                ]
            }).overrideTemplate(VisitorCardDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisitorCardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitorCardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VisitorCard(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visitorCard).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
