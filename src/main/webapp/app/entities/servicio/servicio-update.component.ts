import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IServicio } from 'app/shared/model/servicio.model';
import { ServicioService } from './servicio.service';
import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from 'app/entities/tipo-servicio';
import { ITarea } from 'app/shared/model/tarea.model';
import { TareaService } from 'app/entities/tarea';
import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';
import { PlanMantenimientoService } from 'app/entities/plan-mantenimiento';
import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from 'app/entities/turno';

@Component({
    selector: 'jhi-servicio-update',
    templateUrl: './servicio-update.component.html'
})
export class ServicioUpdateComponent implements OnInit {
    servicio: IServicio;
    isSaving: boolean;

    tiposervicios: ITipoServicio[];

    tareas: ITarea[];

    planmantenimientos: IPlanMantenimiento[];

    turnos: ITurno[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private servicioService: ServicioService,
        private tipoServicioService: TipoServicioService,
        private tareaService: TareaService,
        private planMantenimientoService: PlanMantenimientoService,
        private turnoService: TurnoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ servicio }) => {
            this.servicio = servicio;
        });
        this.tipoServicioService.query().subscribe(
            (res: HttpResponse<ITipoServicio[]>) => {
                this.tiposervicios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tareaService.query().subscribe(
            (res: HttpResponse<ITarea[]>) => {
                this.tareas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.planMantenimientoService.query().subscribe(
            (res: HttpResponse<IPlanMantenimiento[]>) => {
                this.planmantenimientos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.turnoService.query().subscribe(
            (res: HttpResponse<ITurno[]>) => {
                this.turnos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.servicio.id !== undefined) {
            this.subscribeToSaveResponse(this.servicioService.update(this.servicio));
        } else {
            this.subscribeToSaveResponse(this.servicioService.create(this.servicio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IServicio>>) {
        result.subscribe((res: HttpResponse<IServicio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoServicioById(index: number, item: ITipoServicio) {
        return item.id;
    }

    trackTareaById(index: number, item: ITarea) {
        return item.id;
    }

    trackPlanMantenimientoById(index: number, item: IPlanMantenimiento) {
        return item.id;
    }

    trackTurnoById(index: number, item: ITurno) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
