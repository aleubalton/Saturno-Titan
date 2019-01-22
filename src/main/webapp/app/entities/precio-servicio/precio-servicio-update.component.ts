import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPrecioServicio } from 'app/shared/model/precio-servicio.model';
import { PrecioServicioService } from './precio-servicio.service';
import { IModelo } from 'app/shared/model/modelo.model';
import { ModeloService } from 'app/entities/modelo';
import { IServicio } from 'app/shared/model/servicio.model';
import { ServicioService } from 'app/entities/servicio';

@Component({
    selector: 'jhi-precio-servicio-update',
    templateUrl: './precio-servicio-update.component.html'
})
export class PrecioServicioUpdateComponent implements OnInit {
    precioServicio: IPrecioServicio;
    isSaving: boolean;

    modelos: IModelo[];

    servicios: IServicio[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private precioServicioService: PrecioServicioService,
        private modeloService: ModeloService,
        private servicioService: ServicioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ precioServicio }) => {
            this.precioServicio = precioServicio;
        });
        this.modeloService.query().subscribe(
            (res: HttpResponse<IModelo[]>) => {
                this.modelos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.servicioService.query().subscribe(
            (res: HttpResponse<IServicio[]>) => {
                this.servicios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.precioServicio.id !== undefined) {
            this.subscribeToSaveResponse(this.precioServicioService.update(this.precioServicio));
        } else {
            this.subscribeToSaveResponse(this.precioServicioService.create(this.precioServicio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrecioServicio>>) {
        result.subscribe((res: HttpResponse<IPrecioServicio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackModeloById(index: number, item: IModelo) {
        return item.id;
    }

    trackServicioById(index: number, item: IServicio) {
        return item.id;
    }
}
