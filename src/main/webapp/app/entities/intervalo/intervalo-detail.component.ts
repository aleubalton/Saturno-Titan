import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntervalo } from 'app/shared/model/intervalo.model';

@Component({
    selector: 'jhi-intervalo-detail',
    templateUrl: './intervalo-detail.component.html'
})
export class IntervaloDetailComponent implements OnInit {
    intervalo: IIntervalo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ intervalo }) => {
            this.intervalo = intervalo;
        });
    }

    previousState() {
        window.history.back();
    }
}
