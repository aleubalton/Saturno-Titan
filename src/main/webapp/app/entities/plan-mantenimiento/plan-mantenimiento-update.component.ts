import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';
import { PlanMantenimientoService } from './plan-mantenimiento.service';

@Component({
    selector: 'jhi-plan-mantenimiento-update',
    templateUrl: './plan-mantenimiento-update.component.html'
})
export class PlanMantenimientoUpdateComponent implements OnInit {
    planMantenimiento: IPlanMantenimiento;
    isSaving: boolean;

    constructor(private planMantenimientoService: PlanMantenimientoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ planMantenimiento }) => {
            this.planMantenimiento = planMantenimiento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.planMantenimiento.id !== undefined) {
            this.subscribeToSaveResponse(this.planMantenimientoService.update(this.planMantenimiento));
        } else {
            this.subscribeToSaveResponse(this.planMantenimientoService.create(this.planMantenimiento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlanMantenimiento>>) {
        result.subscribe((res: HttpResponse<IPlanMantenimiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
