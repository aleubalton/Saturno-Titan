import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from './tipo-servicio.service';

@Component({
    selector: 'jhi-tipo-servicio-update',
    templateUrl: './tipo-servicio-update.component.html'
})
export class TipoServicioUpdateComponent implements OnInit {
    tipoServicio: ITipoServicio;
    isSaving: boolean;

    constructor(private tipoServicioService: TipoServicioService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoServicio }) => {
            this.tipoServicio = tipoServicio;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoServicio.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoServicioService.update(this.tipoServicio));
        } else {
            this.subscribeToSaveResponse(this.tipoServicioService.create(this.tipoServicio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoServicio>>) {
        result.subscribe((res: HttpResponse<ITipoServicio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
