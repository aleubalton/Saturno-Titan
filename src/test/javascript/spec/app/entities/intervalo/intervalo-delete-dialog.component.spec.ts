/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaturnoTestModule } from '../../../test.module';
import { IntervaloDeleteDialogComponent } from 'app/entities/intervalo/intervalo-delete-dialog.component';
import { IntervaloService } from 'app/entities/intervalo/intervalo.service';

describe('Component Tests', () => {
    describe('Intervalo Management Delete Component', () => {
        let comp: IntervaloDeleteDialogComponent;
        let fixture: ComponentFixture<IntervaloDeleteDialogComponent>;
        let service: IntervaloService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaturnoTestModule],
                declarations: [IntervaloDeleteDialogComponent]
            })
                .overrideTemplate(IntervaloDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IntervaloDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntervaloService);
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
