/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommissionsDetailComponent } from '../../../../../../main/webapp/app/entities/commissions/commissions-detail.component';
import { CommissionsService } from '../../../../../../main/webapp/app/entities/commissions/commissions.service';
import { Commissions } from '../../../../../../main/webapp/app/entities/commissions/commissions.model';

describe('Component Tests', () => {

    describe('Commissions Management Detail Component', () => {
        let comp: CommissionsDetailComponent;
        let fixture: ComponentFixture<CommissionsDetailComponent>;
        let service: CommissionsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [CommissionsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommissionsService,
                    JhiEventManager
                ]
            }).overrideTemplate(CommissionsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommissionsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommissionsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Commissions(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.commissions).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
