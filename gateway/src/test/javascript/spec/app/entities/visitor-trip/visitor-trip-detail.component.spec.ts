/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisitorTripDetailComponent } from '../../../../../../main/webapp/app/entities/visitor-trip/visitor-trip-detail.component';
import { VisitorTripService } from '../../../../../../main/webapp/app/entities/visitor-trip/visitor-trip.service';
import { VisitorTrip } from '../../../../../../main/webapp/app/entities/visitor-trip/visitor-trip.model';

describe('Component Tests', () => {

    describe('VisitorTrip Management Detail Component', () => {
        let comp: VisitorTripDetailComponent;
        let fixture: ComponentFixture<VisitorTripDetailComponent>;
        let service: VisitorTripService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [VisitorTripDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisitorTripService,
                    JhiEventManager
                ]
            }).overrideTemplate(VisitorTripDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisitorTripDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitorTripService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VisitorTrip(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visitorTrip).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
