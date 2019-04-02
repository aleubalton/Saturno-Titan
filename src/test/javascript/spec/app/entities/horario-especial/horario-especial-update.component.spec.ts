/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { HorarioEspecialUpdateComponent } from 'app/entities/horario-especial/horario-especial-update.component';
import { HorarioEspecialService } from 'app/entities/horario-especial/horario-especial.service';
import { HorarioEspecial } from 'app/shared/model/horario-especial.model';

describe('Component Tests', () => {
    describe('HorarioEspecial Management Update Component', () => {
        let comp: HorarioEspecialUpdateComponent;
        let fixture: ComponentFixture<HorarioEspecialUpdateComponent>;
        let service: HorarioEspecialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [HorarioEspecialUpdateComponent]
            })
                .overrideTemplate(HorarioEspecialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HorarioEspecialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorarioEspecialService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new HorarioEspecial(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.horarioEspecial = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new HorarioEspecial();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.horarioEspecial = entity;
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
