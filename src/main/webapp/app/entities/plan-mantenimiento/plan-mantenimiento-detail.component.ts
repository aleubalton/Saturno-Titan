import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanMantenimiento } from 'app/shared/model/plan-mantenimiento.model';

@Component({
    selector: 'jhi-plan-mantenimiento-detail',
    templateUrl: './plan-mantenimiento-detail.component.html'
})
export class PlanMantenimientoDetailComponent implements OnInit {
    planMantenimiento: IPlanMantenimiento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planMantenimiento }) => {
            this.planMantenimiento = planMantenimiento;
        });
    }

    previousState() {
        window.history.back();
    }
}
