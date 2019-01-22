import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IServicio } from 'app/shared/model/servicio.model';
import { ServicioService } from './servicio.service';
import { ITipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';
import { TipoDeServicioService } from 'app/entities/tipo-de-servicio';
import { ITarea } from 'app/shared/model/tarea.model';
import { TareaService } from 'app/entities/tarea';
import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from 'app/entities/turno';

@Component({
    selector: 'jhi-servicio-update',
    templateUrl: './servicio-update.component.html'
})
export class ServicioUpdateComponent implements OnInit {
    servicio: IServicio;
    isSaving: boolean;

    tipodeservicios: ITipoDeServicio[];

    tareas: ITarea[];

    turnos: ITurno[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private servicioService: ServicioService,
        private tipoDeServicioService: TipoDeServicioService,
        private tareaService: TareaService,
        private turnoService: TurnoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ servicio }) => {
            this.servicio = servicio;
        });
        this.tipoDeServicioService.query().subscribe(
            (res: HttpResponse<ITipoDeServicio[]>) => {
                this.tipodeservicios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tareaService.query().subscribe(
            (res: HttpResponse<ITarea[]>) => {
                this.tareas = res.body;
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

    trackTipoDeServicioById(index: number, item: ITipoDeServicio) {
        return item.id;
    }

    trackTareaById(index: number, item: ITarea) {
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
