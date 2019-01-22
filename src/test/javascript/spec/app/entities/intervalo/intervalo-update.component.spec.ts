/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { IntervaloUpdateComponent } from 'app/entities/intervalo/intervalo-update.component';
import { IntervaloService } from 'app/entities/intervalo/intervalo.service';
import { Intervalo } from 'app/shared/model/intervalo.model';

describe('Component Tests', () => {
    describe('Intervalo Management Update Component', () => {
        let comp: IntervaloUpdateComponent;
        let fixture: ComponentFixture<IntervaloUpdateComponent>;
        let service: IntervaloService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [IntervaloUpdateComponent]
            })
                .overrideTemplate(IntervaloUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IntervaloUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntervaloService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Intervalo(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.intervalo = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Intervalo();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.intervalo = entity;
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
