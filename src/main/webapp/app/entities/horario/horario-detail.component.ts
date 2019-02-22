import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHorario } from 'app/shared/model/horario.model';

@Component({
    selector: 'jhi-horario-detail',
    templateUrl: './horario-detail.component.html'
})
export class HorarioDetailComponent implements OnInit {
    horario: IHorario;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horario }) => {
            this.horario = horario;
        });
    }

    previousState() {
        window.history.back();
    }
}
