import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServicio } from 'app/shared/model/servicio.model';

@Component({
    selector: 'jhi-servicio-detail',
    templateUrl: './servicio-detail.component.html'
})
export class ServicioDetailComponent implements OnInit {
    servicio: IServicio;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ servicio }) => {
            this.servicio = servicio;
        });
    }

    previousState() {
        window.history.back();
    }
}
