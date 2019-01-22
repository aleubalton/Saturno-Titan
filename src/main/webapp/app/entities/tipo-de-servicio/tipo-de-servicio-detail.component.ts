import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDeServicio } from 'app/shared/model/tipo-de-servicio.model';

@Component({
    selector: 'jhi-tipo-de-servicio-detail',
    templateUrl: './tipo-de-servicio-detail.component.html'
})
export class TipoDeServicioDetailComponent implements OnInit {
    tipoDeServicio: ITipoDeServicio;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoDeServicio }) => {
            this.tipoDeServicio = tipoDeServicio;
        });
    }

    previousState() {
        window.history.back();
    }
}
