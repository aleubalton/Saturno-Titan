import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHorarioEspecial } from 'app/shared/model/horario-especial.model';

@Component({
    selector: 'jhi-horario-especial-detail',
    templateUrl: './horario-especial-detail.component.html'
})
export class HorarioEspecialDetailComponent implements OnInit {
    horarioEspecial: IHorarioEspecial;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horarioEspecial }) => {
            this.horarioEspecial = horarioEspecial;
        });
    }

    previousState() {
        window.history.back();
    }
}
