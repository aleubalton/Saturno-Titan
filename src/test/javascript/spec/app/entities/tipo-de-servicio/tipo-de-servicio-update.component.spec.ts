/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { TipoDeServicioUpdateComponent } from 'app/entities/tipo-de-servicio/tipo-de-servicio-update.component';
import { TipoDeServicioService } from 'app/entities/tipo-de-servicio/tipo-de-servicio.service';
import { TipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';

describe('Component Tests', () => {
    describe('TipoDeServicio Management Update Component', () => {
        let comp: TipoDeServicioUpdateComponent;
        let fixture: ComponentFixture<TipoDeServicioUpdateComponent>;
        let service: TipoDeServicioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [TipoDeServicioUpdateComponent]
            })
                .overrideTemplate(TipoDeServicioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoDeServicioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeServicioService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeServicio(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeServicio = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoDeServicio();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoDeServicio = entity;
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
