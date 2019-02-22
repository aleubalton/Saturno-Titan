import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrecioServicio } from 'app/shared/model/precio-servicio.model';
import { PrecioServicioService } from './precio-servicio.service';

@Component({
    selector: 'jhi-precio-servicio-delete-dialog',
    templateUrl: './precio-servicio-delete-dialog.component.html'
})
export class PrecioServicioDeleteDialogComponent {
    precioServicio: IPrecioServicio;

    constructor(
        private precioServicioService: PrecioServicioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.precioServicioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'precioServicioListModification',
                content: 'Deleted an precioServicio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-precio-servicio-delete-popup',
    template: ''
})
export class PrecioServicioDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ precioServicio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PrecioServicioDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.precioServicio = precioServicio;
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
