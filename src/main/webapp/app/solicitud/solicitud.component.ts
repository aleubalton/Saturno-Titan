import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDate, NgbDateStruct, NgbCalendar, NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import * as moment from 'moment';

import { SolicitudService } from './solicitud.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IModelo } from 'app/shared/model/modelo.model';
import { ModeloService } from 'app/entities/modelo';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-solicitud',
    templateUrl: './solicitud.component.html',
    styleUrls: ['solicitud.scss']
})
export class SolicitudComponent implements OnInit {
    model: NgbDateStruct;
    minDate: NgbDateStruct;
    maxDate: NgbDateStruct;
    marcas: Set<any>;
    modelos_backend: IModelo[];
    modelos: any[];
    modelosByMarca: any[];
    anios: any[];
    servicios: any[];
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
    isSaving: boolean;
    solicitud = {
        id: 1,
        vehiculo: {
            marca: null,
            modeloId: null,
            modeloNombre: '',
            anio: 2018,
            marca_modelo_anio: '',
            kilometraje: 0,
            patente: ''
        },
        cliente: {
            nombre: '',
            apellido: '',
            email: '',
            telefono: '',
            celular: ''
        },
        tipo_servicio: 'Mantenimiento',
        servicio: {
            nombre: 'General',
            estimacion: 30,
            costo: 3000,
            tipo: 'Diagnóstico',
            tareas: ['Revisión general', 'Generación de presupuesto']
        },
        adicionales: [],
        fecha: moment(),
        horario: '10',
        horario2: '00',
        comentarios: ''
    };

    constructor(
        private jhiAlertService: JhiAlertService,
        private clienteService: ClienteService,
        private modeloService: ModeloService,
        private calendar: NgbCalendar
    ) {
        this.minDate = this.calendar.getToday();
        this.solicitud.fecha = moment();
        this.fecha = new Date(this.minDate['year'], this.minDate['month'], this.minDate['day'] + 40);
        this.maxDate = { year: this.fecha.getFullYear(), month: this.fecha.getMonth(), day: this.fecha.getDate() };
        this.modelos = [
            { nombre: '300', marca: 'Hino' },
            { nombre: '300 Bus', marca: 'Hino' },
            { nombre: 'IS', marca: 'Lexus' },
            { nombre: 'ES', marca: 'Lexus' },
            { nombre: 'GS', marca: 'Lexus' },
            { nombre: 'LS', marca: 'Lexus' },
            { nombre: 'RC', marca: 'Lexus' },
            { nombre: 'LC', marca: 'Lexus' },
            { nombre: 'UX', marca: 'Lexus' },
            { nombre: 'NX', marca: 'Lexus' },
            { nombre: 'RX', marca: 'Lexus' },
            { nombre: '86', marca: 'Toyota' },
            { nombre: 'Etios', marca: 'Toyota' },
            { nombre: 'Yaris', marca: 'Toyota' },
            { nombre: 'Corolla', marca: 'Toyota' },
            { nombre: 'Camry', marca: 'Toyota' },
            { nombre: 'Hilux', marca: 'Toyota' },
            { nombre: 'Prius', marca: 'Toyota' },
            { nombre: 'RAV4', marca: 'Toyota' },
            { nombre: 'SW4', marca: 'Toyota' },
            { nombre: 'Land Cruiser', marca: 'Toyota' },
            { nombre: 'Innova', marca: 'Toyota' }
        ];
        this.marcas = new Set(['TOYOTA', 'LEXUS', 'HINO']);
        this.anios = Array.from(new Array(50), (val, index) => 2018 - index);
        this.servicios = [
            {
                nombre: 'General',
                estimacion: 30,
                costo: 3000,
                tipo: 'Diagnóstico',
                tareas: ['Revisión general', 'Generación de presupuesto']
            },
            {
                nombre: 'Chapa y pintura',
                estimacion: 45,
                costo: 5000,
                tipo: 'Diagnóstico',
                tareas: ['Inspección de carrocería', 'Generación de presupuesto']
            },
            {
                nombre: '10000km',
                estimacion: 30,
                costo: 10000,
                tipo: 'Mantenimiento',
                tareas: [
                    'Cambio de aceite de motor',
                    'Cambio de filtro de aceite',
                    'Inspección de batería',
                    'Inspección de filtro de aire acondicionado',
                    'Inspección de pedal de embrague',
                    'Inspección de bocinas y luces interiores / exteriores',
                    'Inspección de nivel de refrigerante de motor',
                    'Inspección de nivel de fluidos de freno / embrague',
                    'Inspección de pastillas y discos de freno',
                    'Inspección de pedal de freno y freno de mano',
                    'Inspección de presión de inflado de neumáticos'
                ]
            },
            {
                nombre: '20000km',
                estimacion: 45,
                costo: 15000,
                tipo: 'Mantenimiento',
                tareas: ['Cambio de aceite de motor', 'Cambio de filtro de aceite', 'Cambio de filtro de acondicionador de aire']
            },
            {
                nombre: '30000km',
                estimacion: 30,
                costo: 10000,
                tipo: 'Mantenimiento',
                tareas: ['Cambio de aceite de motor', 'Cambio de filtro de aceite']
            },
            {
                nombre: '40000km',
                estimacion: 60,
                costo: 20000,
                tipo: 'Mantenimiento',
                tareas: [
                    'Cambio de aceite de motor',
                    'Cambio de filtro de aceite',
                    'Cambio de filtro de aire',
                    'Cambio de fluidos de freno y embrague',
                    'Cambio de filtro de acondicionador de aire'
                ]
            },
            {
                nombre: '50000km',
                estimacion: 30,
                costo: 10000,
                tipo: 'Mantenimiento',
                tareas: ['Cambio de aceite de motor', 'Cambio de filtro de aceite']
            },
            {
                nombre: 'Cambio bujías',
                estimacion: 35,
                costo: 0,
                tipo: 'Campaña',
                tareas: ['Revisión de bujías', 'Cambio de bujías']
            },
            {
                nombre: 'Cambio amortiguadores',
                estimacion: 45,
                costo: 0,
                tipo: 'Campaña',
                tareas: ['Revisión de amortiguadores', 'Cambio de amortiguadores']
            }
        ];
        this.tiposDeServicios = new Set(this.servicios.map(a => a.tipo));
        this.serviciosByTipo = this.filterByTipo(this.servicios, this.solicitud.tipo_servicio);
        this.solicitud.servicio = this.serviciosByTipo[0];
        this.adicionales = [
            { nombre: 'Cambio de batería', estimacion: 15, costo: 3000, tipo: 'Cambio de batería' },
            { nombre: 'Cambio de neumáticos', estimacion: 30, costo: 4500, tipo: 'Cambio de neumáticos' }
        ];
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

    isWeekend = (date: NgbDate) => this.calendar.getWeekday(date) >= 6;

    ngOnInit() {
        this.isSaving = false;
        this.cliente = new Cliente();
        this.modeloService.query().subscribe(
            (res: HttpResponse<IModelo[]>) => {
                this.modelos_backend = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(this.clienteService.create(this.cliente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
        result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    }

    public refreshMarcas() {
        this.modelosByMarca = this.filterByMarca(this.modelos_backend, this.solicitud.vehiculo.marca);
        this.solicitud.vehiculo.modeloId = this.modelosByMarca[0].id;
        this.solicitud.vehiculo.modeloNombre = this.modelosByMarca[0].nombre;
    }

    public refreshModeloNombre() {
        this.solicitud.vehiculo.modeloNombre = this.modelosByMarca[
            this.modelosByMarca.findIndex(a => a.id === this.solicitud.vehiculo.modeloId)
        ].nombre;
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
            .filter(e => e.tipo.includes(s) || e.nombre.includes(s))
            .sort((a, b) => (a.tipo.includes(s) && !b.tipo.includes(s) ? -1 : b.tipo.includes(s) && !a.tipo.includes(s) ? 1 : 0));
    }
}
