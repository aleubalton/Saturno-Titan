import { Component, OnInit, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDateStruct, NgbCalendar, NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolicitudService } from './solicitud.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Principal, LoginService } from 'app/core';

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
    numeros: any[];
    servicios: any[];
    horarios: any[];
    activeIds: any[];
    fecha: any;
    disable_toggle_1 = false;
    disable_toggle_2 = true;
    disable_toggle_3 = true;

    constructor(private calendar: NgbCalendar) {
        this.minDate = calendar.getToday();
        this.fecha = new Date(this.minDate['year'], this.minDate['month'], this.minDate['day'] + 40);
        this.maxDate = { year: this.fecha.getFullYear(), month: this.fecha.getMonth(), day: this.fecha.getDate() };
        this.numeros = Array.from(new Array(50), (val, index) => 2018 - index);
        this.servicios = [
            'Pre Entrega',
            'Campaña',
            'Mantenimiento',
            'Mantenimiento express',
            'Chapa y pintura',
            'Instalación de accesorios',
            'Cambio de batería',
            'Cambio de neumáticos',
            'Reparación',
            'Otros'
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
