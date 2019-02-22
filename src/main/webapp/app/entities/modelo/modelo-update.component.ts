import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IModelo } from 'app/shared/model/modelo.model';
import { ModeloService } from './modelo.service';

@Component({
    selector: 'jhi-modelo-update',
    templateUrl: './modelo-update.component.html'
})
export class ModeloUpdateComponent implements OnInit {
    modelo: IModelo;
    isSaving: boolean;

    constructor(private modeloService: ModeloService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ modelo }) => {
            this.modelo = modelo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.modelo.id !== undefined) {
            this.subscribeToSaveResponse(this.modeloService.update(this.modelo));
        } else {
            this.subscribeToSaveResponse(this.modeloService.create(this.modelo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IModelo>>) {
        result.subscribe((res: HttpResponse<IModelo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
