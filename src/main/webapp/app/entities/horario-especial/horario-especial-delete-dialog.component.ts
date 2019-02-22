import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHorarioEspecial } from 'app/shared/model/horario-especial.model';
import { HorarioEspecialService } from './horario-especial.service';

@Component({
    selector: 'jhi-horario-especial-delete-dialog',
    templateUrl: './horario-especial-delete-dialog.component.html'
})
export class HorarioEspecialDeleteDialogComponent {
    horarioEspecial: IHorarioEspecial;

    constructor(
        private horarioEspecialService: HorarioEspecialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.horarioEspecialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'horarioEspecialListModification',
                content: 'Deleted an horarioEspecial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-horario-especial-delete-popup',
    template: ''
})
export class HorarioEspecialDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horarioEspecial }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HorarioEspecialDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.horarioEspecial = horarioEspecial;
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
