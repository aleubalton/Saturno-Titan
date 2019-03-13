/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { PlanMantenimientoDetailComponent } from 'app/entities/plan-mantenimiento/plan-mantenimiento-detail.component';
import { PlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';

describe('Component Tests', () => {
    describe('PlanMantenimiento Management Detail Component', () => {
        let comp: PlanMantenimientoDetailComponent;
        let fixture: ComponentFixture<PlanMantenimientoDetailComponent>;
        const route = ({ data: of({ planMantenimiento: new PlanMantenimiento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [PlanMantenimientoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlanMantenimientoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanMantenimientoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.planMantenimiento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
