/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { DiaNoLaborableUpdateComponent } from 'app/entities/dia-no-laborable/dia-no-laborable-update.component';
import { DiaNoLaborableService } from 'app/entities/dia-no-laborable/dia-no-laborable.service';
import { DiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';

describe('Component Tests', () => {
    describe('DiaNoLaborable Management Update Component', () => {
        let comp: DiaNoLaborableUpdateComponent;
        let fixture: ComponentFixture<DiaNoLaborableUpdateComponent>;
        let service: DiaNoLaborableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [DiaNoLaborableUpdateComponent]
            })
                .overrideTemplate(DiaNoLaborableUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DiaNoLaborableUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiaNoLaborableService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DiaNoLaborable(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.diaNoLaborable = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DiaNoLaborable();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.diaNoLaborable = entity;
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
