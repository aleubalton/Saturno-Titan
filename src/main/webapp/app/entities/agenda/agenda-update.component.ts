import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IAgenda } from 'app/shared/model/agenda.model';
import { AgendaService } from './agenda.service';
import { IIntervalo } from 'app/shared/model/intervalo.model';
import { IntervaloService } from 'app/entities/intervalo';
import { IDiaNoLaborable } from 'app/shared/model/dia-no-laborable.model';
import { DiaNoLaborableService } from 'app/entities/dia-no-laborable';

@Component({
    selector: 'jhi-agenda-update',
    templateUrl: './agenda-update.component.html'
})
export class AgendaUpdateComponent implements OnInit {
    agenda: IAgenda;
    isSaving: boolean;

    intervalos: IIntervalo[];

    dianolaborables: IDiaNoLaborable[];
    fechaDesdeDp: any;
    fechaHastaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private agendaService: AgendaService,
        private intervaloService: IntervaloService,
        private diaNoLaborableService: DiaNoLaborableService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agenda }) => {
            this.agenda = agenda;
        });
        this.intervaloService.query().subscribe(
            (res: HttpResponse<IIntervalo[]>) => {
                this.intervalos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.diaNoLaborableService.query().subscribe(
            (res: HttpResponse<IDiaNoLaborable[]>) => {
                this.dianolaborables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.agenda.id !== undefined) {
            this.subscribeToSaveResponse(this.agendaService.update(this.agenda));
        } else {
            this.subscribeToSaveResponse(this.agendaService.create(this.agenda));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAgenda>>) {
        result.subscribe((res: HttpResponse<IAgenda>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIntervaloById(index: number, item: IIntervalo) {
        return item.id;
    }

    trackDiaNoLaborableById(index: number, item: IDiaNoLaborable) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
