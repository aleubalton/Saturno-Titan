import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDateStruct, NgbCalendar, NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolicitudService } from './solicitud.service';

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
    modelos: any[];
    modelosByMarca: any[];
    numeros: any[];
    servicios: any[];
    tiposDeServicios: Set<any>;
    serviciosByTipo: any[];
    adicionales: any[];
    tiposDeAdicionales: Set<any>;
    adicionalesByTipo: any[];
    horarios: any[];
    activeIds: any[];
    fecha: Date;
    disable_toggle_1 = false;
    disable_toggle_2 = true;
    disable_toggle_3 = true;
    solicitud = {
        id: 1,
        vehiculo: {
            marca: 'Toyota',
            modelo: 'Etios',
            anio: 2018,
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
        servicio: '',
        adicionales: [],
        fecha: '',
        horario: '',
        horario2: ''
    };

    constructor(private calendar: NgbCalendar) {
        this.minDate = calendar.getToday();
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
        this.marcas = new Set(this.modelos.map(a => a.marca));
        this.modelosByMarca = this.filterByMarca(this.modelos, this.solicitud.vehiculo.marca);
        this.numeros = Array.from(new Array(50), (val, index) => 2018 - index);
        this.servicios = [
            { nombre: 'General', estimacion: 30, costo: 7000, tipo: 'Diagnóstico' },
            { nombre: 'Chapa y pintura', estimacion: 45, costo: 5000, tipo: 'Diagnóstico' },
            { nombre: '10000km', estimacion: 60, costo: 11000, tipo: 'Mantenimiento' },
            { nombre: '20000km', estimacion: 45, costo: 12000, tipo: 'Mantenimiento' },
            { nombre: '30000km', estimacion: 60, costo: 13000, tipo: 'Mantenimiento' },
            { nombre: '40000km', estimacion: 45, costo: 14000, tipo: 'Mantenimiento' },
            { nombre: '50000km', estimacion: 60, costo: 15000, tipo: 'Mantenimiento' },
            { nombre: 'Cambio bujías', estimacion: 35, costo: 0, tipo: 'Campaña' },
            { nombre: 'Cambio amortiguadores', estimacion: 45, costo: 0, tipo: 'Campaña' }
        ];
        this.tiposDeServicios = new Set(this.servicios.map(a => a.tipo));
        this.serviciosByTipo = this.filterByTipo(this.servicios, this.solicitud.tipo_servicio);
        this.solicitud.servicio = this.serviciosByTipo[0];
        this.adicionales = [
            { nombre: 'Cambio de batería', estimacion: 15, costo: 3000, tipo: 'Cambio de batería' },
            { nombre: 'Traseros', estimacion: 30, costo: 4500, tipo: 'Cambio de neumáticos' },
            { nombre: 'Delanteros', estimacion: 30, costo: 4500, tipo: 'Cambio de neumáticos' },
            { nombre: 'Ambos ejes', estimacion: 60, costo: 9000, tipo: 'Cambio de neumáticos' },
            { nombre: 'Alineación y balanceo', estimacion: 15, costo: 3000, tipo: 'Cambio de neumáticos' }
        ];
        this.tiposDeAdicionales = new Set(this.adicionales.map(a => a.tipo));
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

    ngOnInit() {}

    selectToday() {
        this.model = this.calendar.getToday();
    }

    save() {}

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
        this.modelosByMarca = this.filterByMarca(this.modelos, this.solicitud.vehiculo.marca);
        this.solicitud.vehiculo.modelo = this.modelosByMarca[0].nombre;
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
