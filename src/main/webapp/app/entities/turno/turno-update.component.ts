import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITurno } from 'app/shared/model/turno.model';
import { TurnoService } from './turno.service';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';
import { IVehiculo } from 'app/shared/model/vehiculo.model';
import { VehiculoService } from 'app/entities/vehiculo';
import { IServicio } from 'app/shared/model/servicio.model';
import { ServicioService } from 'app/entities/servicio';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
    selector: 'jhi-turno-update',
    templateUrl: './turno-update.component.html'
})
export class TurnoUpdateComponent implements OnInit {
    turno: ITurno;
    isSaving: boolean;

    agenda: IAgenda[];

    vehiculos: IVehiculo[];

    servicios: IServicio[];

    clientes: ICliente[];
    fechaHora: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private turnoService: TurnoService,
        private agendaService: AgendaService,
        private vehiculoService: VehiculoService,
        private servicioService: ServicioService,
        private clienteService: ClienteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ turno }) => {
            this.turno = turno;
            this.fechaHora = this.turno.fechaHora != null ? this.turno.fechaHora.format(DATE_TIME_FORMAT) : null;
        });
        this.agendaService.query().subscribe(
            (res: HttpResponse<IAgenda[]>) => {
                this.agenda = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.vehiculoService.query().subscribe(
            (res: HttpResponse<IVehiculo[]>) => {
                this.vehiculos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.servicioService.query().subscribe(
            (res: HttpResponse<IServicio[]>) => {
                this.servicios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.turno.fechaHora = this.fechaHora != null ? moment(this.fechaHora, DATE_TIME_FORMAT) : null;
        if (this.turno.id !== undefined) {
            this.subscribeToSaveResponse(this.turnoService.update(this.turno));
        } else {
            this.subscribeToSaveResponse(this.turnoService.create(this.turno));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITurno>>) {
        result.subscribe((res: HttpResponse<ITurno>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAgendaById(index: number, item: IAgenda) {
        return item.id;
    }

    trackVehiculoById(index: number, item: IVehiculo) {
        return item.id;
    }

    trackServicioById(index: number, item: IServicio) {
        return item.id;
    }

    trackClienteById(index: number, item: ICliente) {
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
