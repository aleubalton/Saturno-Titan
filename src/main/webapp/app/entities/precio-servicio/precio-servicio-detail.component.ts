import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrecioServicio } from 'app/shared/model/precio-servicio.model';

@Component({
    selector: 'jhi-precio-servicio-detail',
    templateUrl: './precio-servicio-detail.component.html'
})
export class PrecioServicioDetailComponent implements OnInit {
    precioServicio: IPrecioServicio;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ precioServicio }) => {
            this.precioServicio = precioServicio;
        });
    }

    previousState() {
        window.history.back();
    }
}
