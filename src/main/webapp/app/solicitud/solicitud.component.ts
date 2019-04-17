import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDatepickerConfig, NgbDate, NgbDateStruct, NgbCalendar, NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';

import { SolicitudService } from './solicitud.service';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IVehiculo, Vehiculo } from 'app/shared/model/vehiculo.model';
import { VehiculoService } from 'app/entities/vehiculo/vehiculo.service';
import { ITurno, Turno, Estado } from 'app/shared/model/turno.model';
import { TurnoService } from 'app/entities/turno/turno.service';
import { IModelo } from 'app/shared/model/modelo.model';
import { ModeloService } from 'app/entities/modelo';
import { IServicio } from 'app/shared/model/servicio.model';
import { ServicioService } from 'app/entities/servicio';
import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from 'app/entities/agenda';
import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';
import { DiaNoLaborableService } from 'app/entities/dia-no-laborable';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-solicitud',
    templateUrl: './solicitud.component.html',
    styleUrls: ['solicitud.scss'],
    providers: [NgbDatepickerConfig]
})
export class SolicitudComponent implements OnInit {
    model: NgbDateStruct;
    marcas: any[];
    modelos_backend: IModelo[];
    modelosByMarca: any[];
    anios: number[];
    anioInicio: number;
    anioFin: number;
    agendas_backend: IAgenda[];
    dnls_backend: IDiaNoLaborable[];
    dnls: NgbDate[];
    dls: NgbDate[];
    servicios: IServicio[];
    isDisabled;
    tiposDeServicios: Set<any>;
    serviciosByTipo: any[];
    adicionales: any[];
    horarios: any[];
    activeIds: any[];
    fecha: Date;
    disable_toggle_1 = false;
    disable_toggle_2 = true;
    disable_toggle_3 = true;
    cliente: ICliente;
    vehiculo: IVehiculo;
    turno: ITurno;
    isSaving: boolean;
    regexPatente = new RegExp('^[A-Z]{2}[0-9]{3}[A-Z]{2}|[A-Z]{3}[0-9]{3}$');
    regexEmail = new RegExp(
        '^(([^<>()\\[\\]\\.,;:\\s@&#34;]+(\\.[^<>()\\[\\]\\.,;:\\s@&#34;]+)*)|(&#34;.+&#34;))@' +
            '((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$'
    );
    marcaSelected = null;
    solicitud = {
        id: 1,
        tipo_servicio: 'Mantenimiento',
        servicio: {
            nombre: 'General',
            duracion: 30,
            precio: 3000,
            tipo: 'Diagnóstico',
            tareas: []
        },
        adicionales: [],
        fecha: null,
        horario: null,
        horario2: null
    };

    constructor(
        private jhiAlertService: JhiAlertService,
        private clienteService: ClienteService,
        private vehiculoService: VehiculoService,
        private modeloService: ModeloService,
        private servicioService: ServicioService,
        private agendaService: AgendaService,
        private dnlsService: DiaNoLaborableService,
        private turnoService: TurnoService,
        private config: NgbDatepickerConfig,
        private calendar: NgbCalendar
    ) {
        config.minDate = this.calendar.getToday();
        this.fecha = new Date(config.minDate['year'], config.minDate['month'], config.minDate['day'] + 1);
        config.minDate = { year: this.fecha.getFullYear(), month: this.fecha.getMonth(), day: this.fecha.getDate() };
        this.fecha = new Date(config.minDate['year'], config.minDate['month'], config.minDate['day'] + 45);
        config.maxDate = { year: this.fecha.getFullYear(), month: this.fecha.getMonth(), day: this.fecha.getDate() };
        this.horarios = [
            { hora: '8', disabled: true },
            { hora: '9', disabled: true },
            { hora: '10', disabled: false },
            { hora: '11', disabled: false },
            { hora: '12', disabled: true },
            { hora: '13', disabled: false },
            { hora: '14', disabled: false },
            { hora: '15', disabled: false },
            { hora: '16', disabled: true },
            { hora: '17', disabled: true }
        ];
        this.activeIds = ['toggle-1'];
    }

    ngOnInit() {
        this.isSaving = false;
        this.cliente = new Cliente();
        this.vehiculo = new Vehiculo();
        this.turno = new Turno();
        this.dls = [];
        this.modeloService.query().subscribe(
            (res: HttpResponse<IModelo[]>) => {
                this.modelos_backend = res.body;
                this.marcas = ['TOYOTA', 'LEXUS', 'HINO'];
                this.marcaSelected = this.marcas[0];
                this.refreshMarcas();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.agendaService.query().subscribe(
            (res: HttpResponse<IAgenda[]>) => {
                this.agendas_backend = res.body.filter(a => a.tipoRecurso === 'BAHIA' && a.activa === true);
                const dias = ['DOMINGO', 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO'];
                this.agendas_backend.forEach(a => {
                    for (const date = a.fechaDesde; date.isSameOrBefore(a.fechaHasta); date.add(1, 'd')) {
                        if (
                            a.horarios.filter(h => h.dia === dias[date.day()]).length !== 0 ||
                            a.horarioEspecials.filter(h => date.isSame(h.fecha)).length !== 0
                        ) {
                            this.dls.push(NgbDate.from({ year: date.year(), month: date.month() + 1, day: date.date() }));
                        }
                    }
                });
                this.dnlsService.query().subscribe(
                    (resp: HttpResponse<IDiaNoLaborable[]>) => {
                        this.dnls_backend = resp.body;
                        this.dnls = this.dnls_backend.map(a =>
                            NgbDate.from({ year: a.fecha.year(), month: a.fecha.month() + 1, day: a.fecha.date() })
                        );
                        this.isDisabled = (date: NgbDate) => !this.dls.find(a => a.equals(date)) || this.dnls.find(a => a.equals(date));
                    },
                    (resp: HttpErrorResponse) => this.onError(resp.message)
                );
            },
            (resp: HttpErrorResponse) => this.onError(resp.message)
        );
    }

    save() {
        this.isSaving = true;
        this.turno.fechaHora = this.solicitud.fecha;
        this.turno.codigoReserva = this.randomString(8, '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ');
        this.turno.estado = Estado.RESERVADO;
        this.turno.agendaId = this.agendas_backend[0].id;
        this.subscribeToSaveClienteResponse(this.clienteService.create(this.cliente));
    }

    private subscribeToSaveClienteResponse(result: Observable<HttpResponse<ICliente>>) {
        result.subscribe((res: HttpResponse<ICliente>) => this.onSaveClienteSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveVehiculoResponse(result: Observable<HttpResponse<IVehiculo>>) {
        result.subscribe((res: HttpResponse<IVehiculo>) => this.onSaveVehiculoSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveTurnoResponse(result: Observable<HttpResponse<ITurno>>) {
        result.subscribe((res: HttpResponse<ITurno>) => this.onSaveTurnoSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveClienteSuccess(res: HttpResponse<ICliente>) {
        this.turno.clienteId = res.body.id;
        this.subscribeToSaveVehiculoResponse(this.vehiculoService.create(this.vehiculo));
    }

    private onSaveVehiculoSuccess(res: HttpResponse<IVehiculo>) {
        this.turno.vehiculoId = res.body.id;
        this.subscribeToSaveTurnoResponse(this.turnoService.create(this.turno));
    }

    private onSaveTurnoSuccess(res: HttpResponse<ITurno>) {
        this.turno.id = res.body.id;
    }

    private onSaveError() {}

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    public beforeChange($event: NgbPanelChangeEvent) {
        if ($event.nextState === false) {
            $event.preventDefault();
        }
    }

    public toggleAccordion1() {
        this.activeIds = ['toggle-1'];
        this.disable_toggle_1 = false;
        this.disable_toggle_2 = true;
        this.disable_toggle_3 = true;
    }

    public toggleAccordion2() {
        this.activeIds = ['toggle-2'];
        this.disable_toggle_1 = true;
        this.disable_toggle_2 = false;
        this.disable_toggle_3 = true;
    }

    public toggleAccordion3() {
        this.activeIds = ['toggle-3'];
        this.disable_toggle_1 = true;
        this.disable_toggle_2 = true;
        this.disable_toggle_3 = false;
        this.turno.servicios = [this.solicitud.servicio].concat(this.solicitud.adicionales.filter(e => e != null));
        this.turno.duracion = 0;
        this.turno.costo = 0;
        this.turno.servicios.forEach(serv => {
            this.turno.duracion += serv.duracion;
            this.turno.costo += serv.precio;
        });
    }

    public refreshMarcas() {
        this.modelosByMarca = this.filterByMarca(this.modelos_backend, this.marcaSelected);
        this.vehiculo.modeloId = this.modelosByMarca[0].id;
        this.vehiculo.modeloNombre = this.modelosByMarca[0].nombre;
        this.refreshAnios();
    }

    public refreshModeloNombre() {
        this.vehiculo.modeloNombre = this.modelosByMarca[this.modelosByMarca.findIndex(a => a.id === this.vehiculo.modeloId)].nombre;
        this.refreshAnios();
    }

    public refreshAnios() {
        this.anios = [];
        this.anioInicio = this.modelosByMarca[this.modelosByMarca.findIndex(a => a.id === this.vehiculo.modeloId)].anioInicioProduccion;
        this.anioFin = this.modelosByMarca[this.modelosByMarca.findIndex(a => a.id === this.vehiculo.modeloId)].anioFinProduccion;
        if (!this.anioFin) {
            this.anioFin = moment().year();
        }
        for (let i = this.anioFin; i >= this.anioInicio; i--) {
            this.anios.push(i);
        }
        this.vehiculo.anio = this.anioFin;
        this.servicioService.query().subscribe(
            (resp: HttpResponse<IServicio[]>) => {
                this.servicios = this.filterByAdicional(resp.body, false);
                this.tiposDeServicios = new Set(this.servicios.map(a => a.tipoNombre));
                this.serviciosByTipo = this.filterByTipo(this.servicios, this.solicitud.tipo_servicio);
                this.solicitud.servicio = this.serviciosByTipo[0];
                this.adicionales = this.filterByAdicional(resp.body, true);
            },
            (resp: HttpErrorResponse) => this.onError(resp.message)
        );
    }

    public refreshTipos() {
        this.serviciosByTipo = this.filterByTipo(this.servicios, this.solicitud.tipo_servicio);
        this.solicitud.servicio = this.serviciosByTipo[0];
    }

    public filterByMarca(data, s) {
        return data
            .filter(e => e.marca.includes(s) || e.nombre.includes(s))
            .sort((a, b) => (a.marca.includes(s) && !b.marca.includes(s) ? -1 : b.marca.includes(s) && !a.marca.includes(s) ? 1 : 0));
    }

    public filterByTipo(data, s) {
        return data
            .filter(e => e.tipoNombre.includes(s) || e.nombre.includes(s))
            .sort(
                (a, b) =>
                    a.tipoNombre.includes(s) && !b.tipoNombre.includes(s)
                        ? -1
                        : b.tipoNombre.includes(s) && !a.tipoNombre.includes(s)
                            ? 1
                            : 0
            );
    }

    public filterByAdicional(data, s) {
        return data.filter(
            e => e.tipoAdicional === s && e.planId === this.modelos_backend.find(a => a.id === this.vehiculo.modeloId).planId
        );
    }

    private randomString(length, chars) {
        let result = '';
        for (let i = length; i > 0; --i) {
            result += chars[Math.round(Math.random() * (chars.length - 1))];
        }
        return result;
    }

    public isPatenteOk() {
        return this.regexPatente.test(this.vehiculo.patente);
    }

    public isEmailOk() {
        return this.regexEmail.test(this.cliente.email);
    }

    private checkHorarios() {
        const fecha = this.solicitud.fecha.year() + '-' + (this.solicitud.fecha.month() + 1) + '-' + this.solicitud.fecha.date();
        this.turnoService.queryByFecha({ fecha, agendaId: 1 }).subscribe(
            (res: HttpResponse<ITurno[]>) => {
                console.log(res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        console.log('Chequear horarios disponibles!!');
    }
}
