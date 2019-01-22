/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaturnoTestModule } from '../../../test.module';
import { TipoDeServicioDeleteDialogComponent } from 'app/entities/tipo-de-servicio/tipo-de-servicio-delete-dialog.component';
import { TipoDeServicioService } from 'app/entities/tipo-de-servicio/tipo-de-servicio.service';

describe('Component Tests', () => {
    describe('TipoDeServicio Management Delete Component', () => {
        let comp: TipoDeServicioDeleteDialogComponent;
        let fixture: ComponentFixture<TipoDeServicioDeleteDialogComponent>;
        let service: TipoDeServicioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [TipoDeServicioDeleteDialogComponent]
            })
                .overrideTemplate(TipoDeServicioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoDeServicioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDeServicioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
