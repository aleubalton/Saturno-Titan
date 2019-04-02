import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';
import { PlanMantenimientoService } from './plan-mantenimiento.service';

@Component({
    selector: 'jhi-plan-mantenimiento-delete-dialog',
    templateUrl: './plan-mantenimiento-delete-dialog.component.html'
})
export class PlanMantenimientoDeleteDialogComponent {
    planMantenimiento: IPlanMantenimiento;

    constructor(
        private planMantenimientoService: PlanMantenimientoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planMantenimientoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'planMantenimientoListModification',
                content: 'Deleted an planMantenimiento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plan-mantenimiento-delete-popup',
    template: ''
})
export class PlanMantenimientoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planMantenimiento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlanMantenimientoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.planMantenimiento = planMantenimiento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
