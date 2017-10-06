/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RetailersDetailComponent } from '../../../../../../main/webapp/app/entities/retailers/retailers-detail.component';
import { RetailersService } from '../../../../../../main/webapp/app/entities/retailers/retailers.service';
import { Retailers } from '../../../../../../main/webapp/app/entities/retailers/retailers.model';

describe('Component Tests', () => {

    describe('Retailers Management Detail Component', () => {
        let comp: RetailersDetailComponent;
        let fixture: ComponentFixture<RetailersDetailComponent>;
        let service: RetailersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [RetailersDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RetailersService,
                    JhiEventManager
                ]
            }).overrideTemplate(RetailersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RetailersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RetailersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Retailers(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.retailers).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
