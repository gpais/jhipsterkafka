/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisitorQueriesDetailComponent } from '../../../../../../main/webapp/app/entities/visitor-queries/visitor-queries-detail.component';
import { VisitorQueriesService } from '../../../../../../main/webapp/app/entities/visitor-queries/visitor-queries.service';
import { VisitorQueries } from '../../../../../../main/webapp/app/entities/visitor-queries/visitor-queries.model';

describe('Component Tests', () => {

    describe('VisitorQueries Management Detail Component', () => {
        let comp: VisitorQueriesDetailComponent;
        let fixture: ComponentFixture<VisitorQueriesDetailComponent>;
        let service: VisitorQueriesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [VisitorQueriesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisitorQueriesService,
                    JhiEventManager
                ]
            }).overrideTemplate(VisitorQueriesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisitorQueriesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitorQueriesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VisitorQueries(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visitorQueries).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
