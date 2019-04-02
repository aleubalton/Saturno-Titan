/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaturnoTestModule } from '../../../test.module';
import { TipoServicioUpdateComponent } from 'app/entities/tipo-servicio/tipo-servicio-update.component';
import { TipoServicioService } from 'app/entities/tipo-servicio/tipo-servicio.service';
import { TipoServicio } from 'app/shared/model/tipo-servicio.model';

describe('Component Tests', () => {
    describe('TipoServicio Management Update Component', () => {
        let comp: TipoServicioUpdateComponent;
        let fixture: ComponentFixture<TipoServicioUpdateComponent>;
        let service: TipoServicioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [TipoServicioUpdateComponent]
            })
                .overrideTemplate(TipoServicioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoServicioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoServicioService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoServicio(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoServicio = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoServicio();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoServicio = entity;
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
