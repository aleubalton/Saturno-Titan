/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { PlanMantenimientoUpdateComponent } from 'app/entities/plan-mantenimiento/plan-mantenimiento-update.component';
import { PlanMantenimientoService } from 'app/entities/plan-mantenimiento/plan-mantenimiento.service';
import { PlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';

describe('Component Tests', () => {
    describe('PlanMantenimiento Management Update Component', () => {
        let comp: PlanMantenimientoUpdateComponent;
        let fixture: ComponentFixture<PlanMantenimientoUpdateComponent>;
        let service: PlanMantenimientoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [PlanMantenimientoUpdateComponent]
            })
                .overrideTemplate(PlanMantenimientoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanMantenimientoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanMantenimientoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PlanMantenimiento(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.planMantenimiento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PlanMantenimiento();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.planMantenimiento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
