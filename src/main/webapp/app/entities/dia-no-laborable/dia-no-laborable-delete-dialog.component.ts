import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';
import { DiaNoLaborableService } from './dia-no-laborable.service';

@Component({
    selector: 'jhi-dia-no-laborable-delete-dialog',
    templateUrl: './dia-no-laborable-delete-dialog.component.html'
})
export class DiaNoLaborableDeleteDialogComponent {
    diaNoLaborable: IDiaNoLaborable;

    constructor(
        private diaNoLaborableService: DiaNoLaborableService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.diaNoLaborableService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'diaNoLaborableListModification',
                content: 'Deleted an diaNoLaborable'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dia-no-laborable-delete-popup',
    template: ''
})
export class DiaNoLaborableDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ diaNoLaborable }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DiaNoLaborableDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.diaNoLaborable = diaNoLaborable;
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
