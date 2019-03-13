import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IModelo } from 'app/shared/model/modelo.model';
import { ModeloService } from './modelo.service';
import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';
import { PlanMantenimientoService } from 'app/entities/plan-mantenimiento';

@Component({
    selector: 'jhi-modelo-update',
    templateUrl: './modelo-update.component.html'
})
export class ModeloUpdateComponent implements OnInit {
    modelo: IModelo;
    isSaving: boolean;

    planmantenimientos: IPlanMantenimiento[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private modeloService: ModeloService,
        private planMantenimientoService: PlanMantenimientoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ modelo }) => {
            this.modelo = modelo;
        });
        this.planMantenimientoService.query().subscribe(
            (res: HttpResponse<IPlanMantenimiento[]>) => {
                this.planmantenimientos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPlanMantenimientoById(index: number, item: IPlanMantenimiento) {
        return item.id;
    }
}
