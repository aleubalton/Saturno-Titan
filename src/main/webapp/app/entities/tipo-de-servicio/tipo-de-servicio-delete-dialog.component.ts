import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';
import { TipoDeServicioService } from './tipo-de-servicio.service';

@Component({
    selector: 'jhi-tipo-de-servicio-delete-dialog',
    templateUrl: './tipo-de-servicio-delete-dialog.component.html'
})
export class TipoDeServicioDeleteDialogComponent {
    tipoDeServicio: ITipoDeServicio;

    constructor(
        private tipoDeServicioService: TipoDeServicioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDeServicioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoDeServicioListModification',
                content: 'Deleted an tipoDeServicio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-de-servicio-delete-popup',
    template: ''
})
export class TipoDeServicioDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeServicio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoDeServicioDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoDeServicio = tipoDeServicio;
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
