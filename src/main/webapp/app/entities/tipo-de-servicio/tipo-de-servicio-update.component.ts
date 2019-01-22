import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';
import { TipoDeServicioService } from './tipo-de-servicio.service';

@Component({
    selector: 'jhi-tipo-de-servicio-update',
    templateUrl: './tipo-de-servicio-update.component.html'
})
export class TipoDeServicioUpdateComponent implements OnInit {
    tipoDeServicio: ITipoDeServicio;
    isSaving: boolean;

    constructor(private tipoDeServicioService: TipoDeServicioService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoDeServicio }) => {
            this.tipoDeServicio = tipoDeServicio;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoDeServicio.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoDeServicioService.update(this.tipoDeServicio));
        } else {
            this.subscribeToSaveResponse(this.tipoDeServicioService.create(this.tipoDeServicio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDeServicio>>) {
        result.subscribe((res: HttpResponse<ITipoDeServicio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
