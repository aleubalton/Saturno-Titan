/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { PrecioServicioUpdateComponent } from 'app/entities/precio-servicio/precio-servicio-update.component';
import { PrecioServicioService } from 'app/entities/precio-servicio/precio-servicio.service';
import { PrecioServicio } from 'app/shared/model/precio-servicio.model';

describe('Component Tests', () => {
    describe('PrecioServicio Management Update Component', () => {
        let comp: PrecioServicioUpdateComponent;
        let fixture: ComponentFixture<PrecioServicioUpdateComponent>;
        let service: PrecioServicioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [PrecioServicioUpdateComponent]
            })
                .overrideTemplate(PrecioServicioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrecioServicioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrecioServicioService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PrecioServicio(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.precioServicio = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PrecioServicio();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.precioServicio = entity;
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
