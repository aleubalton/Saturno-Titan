/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaturnoTestModule } from '../../../test.module';
import { HorarioEspecialDeleteDialogComponent } from 'app/entities/horario-especial/horario-especial-delete-dialog.component';
import { HorarioEspecialService } from 'app/entities/horario-especial/horario-especial.service';

describe('Component Tests', () => {
    describe('HorarioEspecial Management Delete Component', () => {
        let comp: HorarioEspecialDeleteDialogComponent;
        let fixture: ComponentFixture<HorarioEspecialDeleteDialogComponent>;
        let service: HorarioEspecialService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [HorarioEspecialDeleteDialogComponent]
            })
                .overrideTemplate(HorarioEspecialDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorarioEspecialDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorarioEspecialService);
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
