/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaturnoTestModule } from '../../../test.module';
import { PlanMantenimientoDeleteDialogComponent } from 'app/entities/plan-mantenimiento/plan-mantenimiento-delete-dialog.component';
import { PlanMantenimientoService } from 'app/entities/plan-mantenimiento/plan-mantenimiento.service';

describe('Component Tests', () => {
    describe('PlanMantenimiento Management Delete Component', () => {
        let comp: PlanMantenimientoDeleteDialogComponent;
        let fixture: ComponentFixture<PlanMantenimientoDeleteDialogComponent>;
        let service: PlanMantenimientoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [PlanMantenimientoDeleteDialogComponent]
            })
                .overrideTemplate(PlanMantenimientoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanMantenimientoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanMantenimientoService);
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
