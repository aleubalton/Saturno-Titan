/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaturnoTestModule } from '../../../test.module';
import { TipoServicioDeleteDialogComponent } from 'app/entities/tipo-servicio/tipo-servicio-delete-dialog.component';
import { TipoServicioService } from 'app/entities/tipo-servicio/tipo-servicio.service';

describe('Component Tests', () => {
    describe('TipoServicio Management Delete Component', () => {
        let comp: TipoServicioDeleteDialogComponent;
        let fixture: ComponentFixture<TipoServicioDeleteDialogComponent>;
        let service: TipoServicioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [TipoServicioDeleteDialogComponent]
            })
                .overrideTemplate(TipoServicioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoServicioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoServicioService);
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
