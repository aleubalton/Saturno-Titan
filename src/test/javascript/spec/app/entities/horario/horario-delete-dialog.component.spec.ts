/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaturnoTestModule } from '../../../test.module';
import { HorarioDeleteDialogComponent } from 'app/entities/horario/horario-delete-dialog.component';
import { HorarioService } from 'app/entities/horario/horario.service';

describe('Component Tests', () => {
    describe('Horario Management Delete Component', () => {
        let comp: HorarioDeleteDialogComponent;
        let fixture: ComponentFixture<HorarioDeleteDialogComponent>;
        let service: HorarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [HorarioDeleteDialogComponent]
            })
                .overrideTemplate(HorarioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorarioService);
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
