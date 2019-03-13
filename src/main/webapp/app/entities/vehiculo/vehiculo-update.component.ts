import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVehiculo } from 'app/shared/model/vehiculo.model';
import { VehiculoService } from './vehiculo.service';
import { IModelo } from 'app/shared/model/modelo.model';
import { ModeloService } from 'app/entities/modelo';

@Component({
    selector: 'jhi-vehiculo-update',
    templateUrl: './vehiculo-update.component.html'
})
export class VehiculoUpdateComponent implements OnInit {
    vehiculo: IVehiculo;
    isSaving: boolean;

    modelos: IModelo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vehiculoService: VehiculoService,
        private modeloService: ModeloService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehiculo }) => {
            this.vehiculo = vehiculo;
        });
        this.modeloService.query().subscribe(
            (res: HttpResponse<IModelo[]>) => {
                this.modelos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vehiculo.id !== undefined) {
            this.subscribeToSaveResponse(this.vehiculoService.update(this.vehiculo));
        } else {
            this.subscribeToSaveResponse(this.vehiculoService.create(this.vehiculo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVehiculo>>) {
        result.subscribe((res: HttpResponse<IVehiculo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackModeloById(index: number, item: IModelo) {
        return item.id;
    }
}
