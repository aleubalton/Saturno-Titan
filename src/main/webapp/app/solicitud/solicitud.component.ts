import { Component, OnInit, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDateStruct, NgbCalendar, NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolicitudService } from './solicitud.service';

@Injectable()
export abstract class NgbDatepickerI18n {
    abstract getWeekdayShortName(weekday: number): string;
    abstract getMonthShortName(month: number): string;
    abstract getMonthFullName(month: number): string;
    abstract getDayAriaLabel(date: NgbDateStruct): string;
}

@Component({
    selector: 'jhi-solicitud',
    templateUrl: './solicitud.component.html',
    styleUrls: ['solicitud.scss']
})
export class SolicitudComponent implements OnInit {
    model: NgbDateStruct;
    minDate: NgbDateStruct;
    maxDate: NgbDateStruct;
    marcas: any[];
    numeros: any[];
    servicios: any[];
    adicionales: any[];
    horarios: any[];
    activeIds: any[];
    fecha: Date;
    disable_toggle_1 = false;
    disable_toggle_2 = true;
    disable_toggle_3 = true;
    solicitud = {
        id: 1,
        vehiculo: {
            marca: '',
            modelo: '',
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
        servicio: '',
        adicionales: []
    };

    constructor(private calendar: NgbCalendar) {
        this.minDate = calendar.getToday();
        this.fecha = new Date(this.minDate['year'], this.minDate['month'], this.minDate['day'] + 40);
        this.maxDate = { year: this.fecha.getFullYear(), month: this.fecha.getMonth(), day: this.fecha.getDate() };
        this.marcas = [
            { marca: 'Hino', modelos: ['300', '300 Bus'] },
            { marca: 'Lexus', modelos: ['IS', 'ES', 'GS', 'LS', 'RC', 'LC', 'UX', 'NX', 'RX'] },
            {
                marca: 'Toyota',
                modelos: ['86', 'Etios', 'Yaris', 'Corolla', 'Camry', 'Hilux', 'Prius', 'RAV4', 'SW4', 'Land Cruiser', 'Innova']
            }
        ];
        this.numeros = Array.from(new Array(50), (val, index) => 2018 - index);
        this.servicios = [
            { descripcion: 'Diagnóstico', estimacion: 30, subservicios: ['Diagnóstico general', 'Chapa y pintura'] },
            {
                descripcion: 'Mantenimiento',
                estimacion: 60,
                subservicios: ['Service 10000km', 'Service 20000km', 'Service 30000km', 'Service 40000km', 'Service 50000km']
            },
            { descripcion: 'Campaña', estimacion: 45, subservicios: ['Campaña cambio bujías', 'Campaña cambio amortiguadores'] }
        ];
        this.adicionales = [
            { descripcion: 'Cambio de batería', estimacion: 15, subservicios: ['Cambio de batería'] },
            {
                descripcion: 'Cambio de neumáticos',
                estimacion: 30,
                subservicios: ['Neumáticos traseros', 'Neumáticos delanteros', 'Ambas', 'Rotación y balanceo']
            }
        ];
        this.horarios = ['8:--', '9:--', '10:--', '11:--', '12:--', '13:--', '14:--', '15:--', '16:--', '17:--'];
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
}
